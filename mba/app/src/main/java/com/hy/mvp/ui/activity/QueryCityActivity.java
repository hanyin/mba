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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.bean.Region;
import com.hy.mvp.greendao.manager.GreenDaoManager;
import com.hy.mvp.greendao.manager.RegionManager;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;
import com.hy.mvp.ui.view.City;
import com.hy.mvp.ui.view.MyLetterListView;
import com.hy.mvp.ui.view.PingYinUtil;
import com.hy.mvp.view.MClearEditText;

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
 * 作者：hanyin on 2017/8/13 20:12
 * 邮箱：hanyinit@163.com
 */

public class QueryCityActivity extends BaseActivity implements AbsListView.OnScrollListener {
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
    private ArrayList<City> allCity_lists; // 所有城市列表
    private ArrayList<City> city_lists;// 城市列表
    private ArrayList<City> city_hot;
    private ArrayList<City> city_result;
    @BindView(R.id.sh)
    EditText sh;
    @BindView(R.id.tv_noresult)
     TextView tv_noresult;
    private boolean isNeedFresh;

    @Override
    public void initData() {
        allCity_lists = new ArrayList<>();
        city_hot = new ArrayList<>();
        city_result = new ArrayList<>();
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
                    city_result.clear();
                    letterListView.setVisibility(View.GONE);
                    personList.setVisibility(View.GONE);
                     getResultCityList(s.toString());
                    if (city_result.size() <= 0) {
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
                   Intent intent = new Intent();
                   intent.putExtra("city",allCity_lists.get(position).getName());
                   intent.putExtra("index",position);
                   setResult(2,intent);
                   finish();
                    Toast.makeText(getApplicationContext(),
                            allCity_lists.get(position).getName(),
                            Toast.LENGTH_SHORT).show();
            }
        });
        personList.setAdapter(adapter);
        personList.setOnScrollListener(this);
        resultListAdapter = new ResultListAdapter(this, city_result);
        resultList.setAdapter(resultListAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("city",city_result.get(position).getName());
                intent.putExtra("index",position);
                setResult(2,intent);
                finish();
                Toast.makeText(getApplicationContext(),
                        city_result.get(position).getName(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
        initOverlay();
        cityInit();
        setAdapter(allCity_lists);

    }
    @Override
    public void initView() {
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_query_city);
        ButterKnife.bind(this);
        showTitleBack();
        setNavigationIcon(R.mipmap.btn_back);
        setTitleText("切换城市");
    }
    @Override
    public void initListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    private void cityInit() {
        city_lists = getCityList();
        allCity_lists.addAll(city_lists);
    }

    @SuppressWarnings("unchecked")
    private ArrayList<City> getCityList() {
        List<Region> regions = RegionManager.getInstance().queryRegion();
        ArrayList<City> list = new ArrayList<City>();
        for (int i = 0; i <regions.size() ; i++) {
            City city = new City();
            city.setName(regions.get(i).getRegionName());
            city.setPinyi(regions.get(i).getPinyin());
            list.add(city);
        }

        Collections.sort(list, comparator);
        return list;
    }

    @SuppressWarnings("unchecked")
    private void getResultCityList(String keyword) {
       /* List<Region> regions = RegionManager.getInstance().queryByPinyin(keyword);
        if(regions!=null&regions.size()>0){
            for (int i = 0; i < regions.size(); i++) {
                City city = new City();
                city.setName(regions.get(i).getRegionName());
                city.setPinyi(regions.get(i).getPinyin());
                city_result.add(city);
            }
        }*/
        String tablename =  GreenDaoManager.getInstance().getSession().getRegionDao().getTablename();
        Database db = GreenDaoManager.getInstance().getSession().getDatabase();
        Cursor cursor = db.rawQuery(
                "select * from "+tablename+" where REGION_NAME like \"%" + keyword
                        + "%\" or PINYIN like \"%" + keyword + "%\"", null);
        while (cursor.moveToNext()) {
            City city = new City(cursor.getString(2), cursor.getString(11));
            city_result.add(city);
        }
        cursor.close();
        Collections.sort(city_result, comparator);
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyi().substring(0, 1);
            String b = rhs.getPinyi().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    private void setAdapter(List<City> list) {
        adapter = new ListAdapter(this, list);
        personList.setAdapter(adapter);
    }

    private class ResultListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<City> results = new ArrayList<City>();

        public ResultListAdapter(Context context, ArrayList<City> results) {
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
                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(results.get(position).getName());
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }

    public class ListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<City> list;
        final int VIEW_TYPE = 5;

        public ListAdapter(Context context, List<City> list
                          ) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(list.get(i).getPinyi());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1)
                        .getPinyi()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getPinyi());
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

        ViewHolder holder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView city;
            int viewType = getItemViewType(position);

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.list_item, null);
                    holder = new ViewHolder();
                    holder.alpha = (TextView) convertView
                            .findViewById(R.id.alpha);
                    holder.name = (TextView) convertView
                            .findViewById(R.id.name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                    holder.name.setText(list.get(position).getName());
                    String currentStr = getAlpha(list.get(position).getPinyi());
                    String previewStr = (position - 1) >= 0 ? getAlpha(list
                            .get(position - 1).getPinyi()) : " ";
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
            TextView name; // 城市名字
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
            String name = allCity_lists.get(firstVisibleItem).getName();
            String pinyin = allCity_lists.get(firstVisibleItem).getPinyi();
           /* if (firstVisibleItem < 4) {
                text = name;
            } else {*/
                text = PingYinUtil.converterToFirstSpell(pinyin)
                        .substring(0, 1).toUpperCase();
            //}
            overlay.setText(text);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            handler.postDelayed(overlayThread, 1000);
        }
    }
}
