package com.hy.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.bean.Region;
import com.hy.mvp.greendao.manager.AddrBookManager;
import com.hy.mvp.greendao.manager.GreenDaoManager;
import com.hy.mvp.greendao.manager.RegionManager;
import com.hy.mvp.ui.adapter.NewFriendsAdapter;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.AddrBook;
import com.hy.mvp.ui.view.City;
import com.hy.mvp.ui.view.MyLetterListView;
import com.hy.mvp.ui.view.PingYinUtil;
import com.hy.mvp.utils.GlideUtils;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  2017/10/24 13:41
 *  hanyin
 *  hanyinit@163.com
 */

public class MyAddressBookActivity extends BaseActivity implements AbsListView.OnScrollListener {

    private BaseAdapter adapter;
    private ResultListAdapter resultListAdapter;
    @BindView(R.id.list_view)
    ListView personList;
    @BindView(R.id.search_result)
    ListView resultList;
    TextView overlay; // 对话框首字母textview
    @BindView(R.id.MyLetterListView01)
    MyLetterListView letterListView; // A-Z listview
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    private Handler handler;
    private OverlayThread overlayThread; // 显示首字母对话框
    private ArrayList<AddrBook> allAddrBook_lists;
    private ArrayList<AddrBook> AddrBook_result;
    @BindView(R.id.sh)
    EditText sh;
    @BindView(R.id.tv_noresult)
    TextView tv_noresult;
    @BindView(R.id.llNewFriend)
    LinearLayout llNewFriend;
    private boolean isNeedFresh;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_my_address_book);
        setTitleText("通讯录");
        setTitleRightImage(R.mipmap.nav_search);
        showTitleBack();
        setNavigationIcon(R.mipmap.btn_back);
        ButterKnife.bind(this);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        startActivity(new Intent(MyAddressBookActivity.this,AddNewFriends.class));
    }

    @Override
    public void initData() {
        allAddrBook_lists = new ArrayList<>();
        AddrBook_result = new ArrayList<>();
        sh.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    letterListView.setVisibility(View.VISIBLE);
                    personList.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                    tv_noresult.setVisibility(View.GONE);
                } else {
                    AddrBook_result.clear();
                    letterListView.setVisibility(View.GONE);
                    personList.setVisibility(View.GONE);
                    getResultAddrBooksList(s.toString());
                    if (AddrBook_result.size() <= 0) {
                        tv_noresult.setVisibility(View.VISIBLE);
                        resultList.setVisibility(View.GONE);
                    } else {
                        tv_noresult.setVisibility(View.GONE);
                        resultList.setVisibility(View.VISIBLE);
                        resultListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        isNeedFresh = true;
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MyAddressBookActivity.this,ChatActivity.class);
                intent.putExtra("id",allAddrBook_lists.get(position).getContactsId());
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        allAddrBook_lists.get(position).getContacts_name(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        personList.setAdapter(adapter);
        personList.setOnScrollListener(this);
        resultListAdapter = new ResultListAdapter(this, AddrBook_result);
        resultList.setAdapter(resultListAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               /* Intent intent = new Intent();
                intent.putExtra("city",city_result.get(position).getName());
                intent.putExtra("index",position);
                setResult(2,intent);
                finish();*/
                Intent intent = new Intent(MyAddressBookActivity.this,ChatActivity.class);
                intent.putExtra("id",AddrBook_result.get(position).getContactsId());
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        AddrBook_result.get(position).getContacts_name(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
        initOverlay();
        cityInit();
        setAdapter(allAddrBook_lists);

    }

    @Override
    public void initListener() {
        super.initListener();
        llNewFriend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.llNewFriend:
                startActivity(new Intent(MyAddressBookActivity.this, NewFriendsActivity.class));
                break;
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    private void cityInit() {
        ArrayList<AddrBook> addrBooks = (ArrayList<AddrBook>) AddrBookManager.getInstance().queryAddrBooks();
        allAddrBook_lists.addAll(addrBooks);
        Collections.sort(allAddrBook_lists, comparator);
    }

    private void getResultAddrBooksList(String keyword) {
        List<AddrBook> addrBooks = AddrBookManager.getInstance().queryByPinyin(keyword);
        AddrBook_result.addAll(addrBooks);
        Collections.sort(AddrBook_result, comparator);
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<AddrBook>() {
        @Override
        public int compare(AddrBook lhs, AddrBook rhs) {
            String a = lhs.getPingyin().substring(0, 1);
            String b = rhs.getPingyin().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    private void setAdapter(List<AddrBook> list) {
        adapter = new ListAdapter(this, list);
        personList.setAdapter(adapter);
    }

    private class ResultListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<AddrBook> results = new ArrayList<AddrBook>();

        public ResultListAdapter(Context context, ArrayList<AddrBook> results) {
            inflater = LayoutInflater.from(context);
            this.results = results;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.address_book_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.tvRealName);
                viewHolder.imageHead = (ImageView) convertView.findViewById(R.id.imageHead);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(results.get(position).getContacts_name());
            GlideUtils.load(MyAddressBookActivity.this,results.get(position).getContacts_portrait().getUrl(),viewHolder.imageHead);
            return convertView;
        }

        class ViewHolder {
            TextView name;
            ImageView imageHead;
        }
    }

    public class ListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<AddrBook> list;
        final int VIEW_TYPE = 5;

        public ListAdapter(Context context, List<AddrBook> list
        ) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(list.get(i).getPingyin());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1)
                        .getPingyin()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getPingyin());
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 4 ? position : 4;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        ListAdapter.ViewHolder holder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView city;
            int viewType = getItemViewType(position);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                holder = new ListAdapter.ViewHolder();
                holder.alpha = (TextView) convertView
                        .findViewById(R.id.alpha);
                holder.name = (TextView) convertView
                        .findViewById(R.id.name);
                holder.imageHead = (ImageView) convertView.findViewById(R.id.imageHead);
                convertView.setTag(holder);
            } else {
                holder = (ListAdapter.ViewHolder) convertView.getTag();
            }
            holder.name.setText(list.get(position).getContacts_name());
            GlideUtils.load(MyAddressBookActivity.this,list.get(position).getContacts_portrait().getUrl(),holder.imageHead);
            String currentStr = getAlpha(list.get(position).getPingyin());
            String previewStr = (position - 1) >= 0 ? getAlpha(list
                    .get(position - 1).getPingyin()) : " ";
            if (!previewStr.equals(currentStr)) {
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(currentStr);
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView alpha; // 首字母标题
            TextView name; // 通讯录名字
            ImageView imageHead;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    private boolean mReady;

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private boolean isScroll = false;

    private class LetterListViewListener implements
            MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                personList.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }
        if (mReady) {
            String text;
            String name = allAddrBook_lists.get(firstVisibleItem).getContacts_name();
            String pinyin = allAddrBook_lists.get(firstVisibleItem).getPingyin();
            text = PingYinUtil.converterToFirstSpell(pinyin)
                    .substring(0, 1).toUpperCase();
            overlay.setText(text);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            handler.postDelayed(overlayThread, 1000);
        }
    }
}
