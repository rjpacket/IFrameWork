//package com.rjp.fastframework.views;
//
//import android.arch.lifecycle.Observer;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.Rect;
//import android.graphics.Typeface;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.OrientationHelper;
//import android.support.v7.widget.RecyclerView;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewStub;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.beiins.DollyApplication;
//import com.beiins.activity.ConfigActivity;
//import com.beiins.activity.SearchActivity;
//import com.beiins.aop.SingleClick;
//import com.beiins.baseRecycler.base.BaseRViewFragment;
//import com.beiins.baseRecycler.base.RViewAdapter;
//import com.beiins.baseRecycler.helper.RViewHelper;
//import com.beiins.baseRecycler.helper.RefreshHelper;
//import com.beiins.baseRecycler.holder.RViewHolder;
//import com.beiins.baseRecycler.inteface.RViewItem;
//import com.beiins.bean.ArticleSimpleBean;
//import com.beiins.bean.ClickBean;
//import com.beiins.bean.TopicSimpleBean;
//import com.beiins.config.URLConfig;
//import com.beiins.dolly.R;
//import com.beiins.fragment.homeRViewItems.ItemArticleTitle;
//import com.beiins.fragment.homeRViewItems.ItemBanner;
//import com.beiins.fragment.homeRViewItems.ItemBlank;
//import com.beiins.fragment.homeRViewItems.ItemSimpleArticle;
//import com.beiins.http.DollyNetworkManager;
//import com.beiins.http.core.HttpHelper;
//import com.beiins.http.core.ICallback;
//import com.beiins.log.BehaviorLog;
//import com.beiins.log.DLog;
//import com.beiins.log.EventManager;
//import com.beiins.log.EventName;
//import com.beiins.utils.DollyUtils;
//import com.beiins.utils.GlideImageLoader;
//import com.beiins.utils.LiveDataBus;
//import com.beiins.view.tab.TabLayout;
//import com.bumptech.glide.Glide;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.hy.contacts.HyUtils;
//import com.hy.util.HyWebSynCookieUtil;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.youth.banner.Banner;
//import com.youth.banner.listener.OnBannerListener;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.Response;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import static com.beiins.utils.LiveDataBus.TAG_CLICK_HOME_BLANK;
//import static com.beiins.utils.LiveDataBus.TAG_CLICK_HOME_STICKY_TITLE;
//
//public class HomeFragment extends BaseRViewFragment<ArticleSimpleBean> {
//    //顶部banner
//    public static final int TYPE_HOME_FRAGMENT_BANNER = 0;
//    //导诊护士栏
//    public static final int TYPE_HOME_FRAGMENT_GUIDE = TYPE_HOME_FRAGMENT_BANNER + 1;
//    //四个进入其他页面的view（智能诊断、保单管家、产品分析、风险百科）
//    public static final int TYPE_HOME_FRAGMENT_FOUR_VIEW = TYPE_HOME_FRAGMENT_GUIDE + 1;
//    //定制保障方案栏
//    public static final int TYPE_HOME_FRAGMENT_CUSTOM = TYPE_HOME_FRAGMENT_FOUR_VIEW + 1;
//    //两个进入其他页面的view（预核保、理赔协助）
//    public static final int TYPE_HOME_FRAGMENT_TWO_VIEW = TYPE_HOME_FRAGMENT_CUSTOM + 1;
//    //横向滑动的list（小贝话题）
//    public static final int TYPE_HOME_FRAGMENT_TOPIC_LIST = TYPE_HOME_FRAGMENT_TWO_VIEW + 1;
//    //独家测评标题
//    public static final int TYPE_HOME_FRAGMENT_ARTICLE_FIRST_TITLE = TYPE_HOME_FRAGMENT_TOPIC_LIST + 1;
//    //文章测评上方的标题栏（吸顶）
//    public static final int TYPE_HOME_FRAGMENT_ARTICLE_TITLE = TYPE_HOME_FRAGMENT_ARTICLE_FIRST_TITLE + 1;
//    //文章测评整个recyclerView
//    public static final int TYPE_HOME_FRAGMENT_ARTICLE_LIST = TYPE_HOME_FRAGMENT_ARTICLE_TITLE + 1;
//    //单个文章测评的view
//    public static final int TYPE_HOME_FRAGMENT_SIMPLE_ARTICLE = TYPE_HOME_FRAGMENT_ARTICLE_LIST + 1;
//    //无数据时的空白占位view
//    public static final int TYPE_HOME_FRAGMENT_BLANK = TYPE_HOME_FRAGMENT_SIMPLE_ARTICLE + 1;
//    //新的小贝保险测评页（只一个图片）
//    public static final int TYPE_HOME_FRAGMENT_ONE_BROAD_VIEW = TYPE_HOME_FRAGMENT_BLANK + 1;
//    //私人定制和合规医诊（横排两个图片）
//    public static final int TYPE_HOME_FRAGMENT_TWO_BROAD_VIEW = TYPE_HOME_FRAGMENT_ONE_BROAD_VIEW + 1;
//    public final int POSITION_ARTICLE_FIRST = 7;//第一个独家测评文章的位置，用于切换title时复原位置等
//    //HomeFragment的布局view
//    private View fragmentLayout;
//    //主adapter
//    private RViewAdapter<ArticleSimpleBean> homeAdapter;
//    //独家测评的当前位于页面，默认初始值第一页：0
//    private int currentPage = 0;
//    private Handler handler;
//    private TextView searchTextView;
//    //搜索框内默认文案
//    private String searchHintText;
//    private int[] requestPageList = {0, 0, 0, 0, 0, 0};//独家测评每一页请求用的page
//    private int[] positionList = {0, 0, 0, 0, 0, 0};//独家测评每一页请求的position
//    private ArrayList<String> requestTypeList;
//    private final int MAX_ARTICLE_SIZE = 5;//独家测评列表数（热门，重疾，寿险，医疗，意外）
//    private SmartRefreshLayout refreshLayout;//下拉刷新和上拉加载的组件
//    private ArrayList<ArrayList<ArticleSimpleBean>> cacheArticleList;
//    private boolean overGuideThread = false;
//    private BroadcastReceiver broadcastReceiver;
//    //存储首页从智能客服到理赔协助的url
//    //主 RecyclerView
//    private RecyclerView homeRecyclerView;
//    private ArrayList<TextView> stickTitleList;//吸顶的title
//    private ArrayList<View> stickMarkList;
//    private ArrayList<String> articleNameList;
//    private LinearLayoutManager homeLayoutManager;
//    private int stickPosition = 6;//吸顶布局的位置
//    private final static String contextName = "HomeFragment";
//    private ArrayList<String> imgArrayList;
//    private ArrayList<ClickBean> urlArrayList;
//    private ArrayList<ArticleSimpleBean> homeArticleBeans;
//    private ItemArticleTitle itemArticleTitle;
//    private ItemBlank itemBlank;
//    private ItemBanner itemBanner;
//    private ViewStub homeContainer;
//
//    private static final String[] types = new String[]{
//            "evaluation_hot",
//            "serious_illness",
//            "life_insurance",
//            "medical",
//            "accident"
//    };
//    //banner
//    private ImageView bannerDefault;
//    private Banner banner;
//    //one broad
//    private RelativeLayout rlOneBroad;
//    //two broad
//    private RelativeLayout rlTwoLeft;
//    private RelativeLayout rlTwoRight;
//    //four broad
//    private RelativeLayout rlFour1;
//    private RelativeLayout rlFour2;
//    private RelativeLayout rlFour3;
//    private RelativeLayout rlFour4;
//    //topic list
//    private RecyclerView topicList;
//    private TextView tvEmptyView;
//
//    private TabLayout tabLayout;
//    //当前选项卡
//    private int currentPosition;
//    //当前选项卡页码
//    private int page;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        if (fragmentLayout == null) {
//            handler = new Handler();
//            fragmentLayout = inflater.inflate(R.layout.f_home, null);
//
//            context = getContext();
//            homeContainer = fragmentLayout.findViewById(R.id.stub_home_recycler_view);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    homeContainer.inflate();
//                    helper = new RViewHelper.Builder<>(HomeFragment.this, HomeFragment.this).build();
//                    initHomeTopBarView();
//                    initRecyclerView();
//                    requestBanner(false);
//                    requestHomeList();
//                }
//            }, 100);
//
//            imgArrayList = new ArrayList<>();
//            urlArrayList = new ArrayList<>();
//            initData();
//            initSearch();
//            registerBroadcast();
//            registerObserve();
//
//            //预加载热门页面内容
//            cacheArticleList = new ArrayList<>();//用于缓存测评列表各页数据
//            for (int i = 0; i < MAX_ARTICLE_SIZE; i++) {
//                cacheArticleList.add(new ArrayList<ArticleSimpleBean>());
//            }
//
//            initDebug();
//        }
//        return fragmentLayout;
//    }
//
//    /**
//     * 首页顶部初始化
//     */
//    private void initHomeTopBarView() {
//        initBanner();
//        initOneBroad();
//        initTwoBroad();
//        initFourBroad();
//        initTopicList();
//        initTabLayout();
//    }
//
//    private void initTopicList() {
//        topicList = fragmentLayout.findViewById(R.id.list_topic);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        topicList.setLayoutManager(layoutManager);
//        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
//        List<TopicSimpleBean> topicData = getLocalTopicData();
//        topicList.setAdapter(new RViewAdapter<>(topicData, new RViewItem<TopicSimpleBean>() {
//            @Override
//            public int getItemLayout() {
//                return R.layout.v_home_topic_simple;
//            }
//
//            @Override
//            public boolean isItemView(TopicSimpleBean entity, int position) {
//                return true;
//            }
//
//            @Override
//            public void convert(RViewHolder holder, final TopicSimpleBean bean, int position) {
//                Glide.with(context).load(bean.iconUrl).into((ImageView) holder.getView(R.id.topic_simple_icon));//小贝话题图片是本地的，不用加载占位图
//                TextView tvTitle = holder.getView(R.id.topic_simple_title);
//                TextView tvUserName = holder.getView(R.id.topic_user_name);
//                TextView tvContent = holder.getView(R.id.topic_content);
//                tvTitle.setText(bean.title);
//                Glide.with(context).load(bean.userPortrait).into((ImageView) holder.getView(R.id.topic_user_portrait));
//                tvUserName.setText(bean.userName);
//                tvContent.setText(bean.content);
//                holder.getView(R.id.layout_simple_topic).setOnClickListener(new View.OnClickListener() {
//
//                    @SingleClick
//                    @Override
//                    public void onClick(View v) {
//                        EventManager.EventSender.create(EventName.HOME_EVENT_TOPIC_ITEM)
//                                .setContext(contextName)
//                                .put("title", bean.title)
//                                .send();
//
//                        context.sendBroadcast(new Intent(DollyUtils.ACTION_REQUEST_QRCODE));
//                    }
//                });
//            }
//        }));
//        topicList.setItemAnimator(new DefaultItemAnimator());
//    }
//
//    /**
//     * 小贝话题，采用本地数据
//     */
//    private ArrayList<TopicSimpleBean> getLocalTopicData() {
//        ArrayList<TopicSimpleBean> topicEg = new ArrayList<>();
//        TopicSimpleBean bean = new TopicSimpleBean();
//        bean.iconUrl = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.icon_topic_1).toString();
//        bean.title = "遇到过的停售套路，哪一次最扎心？";
//        bean.userPortrait = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.icon_topic_portrait_1).toString();
//        bean.userName = "GY";
//        bean.content = "国寿的老年险，真的对比我之前咨询并购买的恒安老年险简直有毒，同样的还有.....";
//        topicEg.add(bean);
//
//        TopicSimpleBean bean2 = new TopicSimpleBean();
//        bean2.iconUrl = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.icon_topic_2).toString();
//        bean2.title = "相知恨晚的投保尝试有哪些？";
//        bean2.userPortrait = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.icon_topic_portrait_2).toString();
//        bean2.userName = "小贝壳";
//        bean2.content = "买保险不要听销售员忽悠，根据自己实际情况买适合的，先买大人，要如果遇到了.....";
//        topicEg.add(bean2);
//
//        TopicSimpleBean bean3 = new TopicSimpleBean();
//        bean3.iconUrl = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.icon_topic_3).toString();
//        bean3.title = "有哪些沙雕保险逻辑，最让人无语？";
//        bean3.userPortrait = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.icon_topic_portrait_3).toString();
//        bean3.userName = "小ZZ";
//        bean3.content = "这类型的理财险很多都是被销售员什么复理论忽悠，然后头脑发热就买了不少.....";
//        topicEg.add(bean3);
//
//        return topicEg;
//    }
//
//    private void initTabLayout() {
//        tabLayout = fragmentLayout.findViewById(R.id.home_tab_layout);
//        ArrayList<String> titles = new ArrayList<>();
//        titles.add("热门");
//        titles.add("重疾");
//        titles.add("寿险");
//        titles.add("医疗");
//        titles.add("意外");
//        tabLayout.setTitles(titles);
//
//        tvEmptyView = fragmentLayout.findViewById(R.id.tv_empty_view);
//    }
//
//    private void initFourBroad() {
//        rlFour1 = fragmentLayout.findViewById(R.id.layout_four_view_1);
//        rlFour2 = fragmentLayout.findViewById(R.id.layout_four_view_2);
//        rlFour3 = fragmentLayout.findViewById(R.id.layout_four_view_3);
//        rlFour4 = fragmentLayout.findViewById(R.id.layout_four_view_4);
//
//        final ClickBean analysisClickBean = new ClickBean()
//                .setUrl(URLConfig.HOME_PRODUCT_EVALUATION_URL)
//                .setTitle("产品分析")
//                .showTitle();
//        final ClickBean preClickBean = new ClickBean()
//                .setUrl(URLConfig.HOME_PRE_UNDER_WRITING_URL)
//                .setTitle("预核保")
//                .showTitle()
//                .showShare();
//        final ClickBean manageClickBean = new ClickBean()
//                .setUrl(URLConfig.HOME_MEDICINE_CHEST_URL)
//                .setTitle("保单管家")
//                .showTitle();
//        final ClickBean claimsClickBean = new ClickBean()
//                .setUrl(URLConfig.HOME_CLAIMS_ASSISTANCE_URL)
//                .setTitle("理赔/纠纷协助")
//                .showTitle()
//                .showShare();
//
//        rlFour1.setOnClickListener(new View.OnClickListener() {
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_PRODUCT_EVALUATION)
//                        .setContext(contextName)
//                        .setUrl(URLConfig.HOME_PRODUCT_EVALUATION_URL)
//                        .send();
//
//                HyUtils.startHyActivity(context, analysisClickBean);
//            }
//        });
//        rlFour2.setOnClickListener(new View.OnClickListener() {
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_PRE_UNDER_WRITING)
//                        .setContext(contextName)
//                        .setUrl(URLConfig.HOME_PRE_UNDER_WRITING_URL)
//                        .send();
//
//                HyUtils.startHyActivity(context, preClickBean);
//            }
//        });
//        rlFour3.setOnClickListener(new View.OnClickListener() {
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_MEDICINE_CHEST)
//                        .setContext(contextName)
//                        .setUrl(URLConfig.HOME_MEDICINE_CHEST_URL)
//                        .send();
//
//                HyUtils.startHyActivity(context, manageClickBean);
//            }
//        });
//        rlFour4.setOnClickListener(new View.OnClickListener() {
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_CLAIMS_ASSISTANCE)
//                        .setContext(contextName)
//                        .setUrl(URLConfig.HOME_CLAIMS_ASSISTANCE_URL)
//                        .send();
//
//                HyUtils.startHyActivity(context, claimsClickBean);
//            }
//        });
//    }
//
//    private void initTwoBroad() {
//        //two broad
//        rlTwoLeft = fragmentLayout.findViewById(R.id.layout_left);
//        rlTwoRight = fragmentLayout.findViewById(R.id.layout_right);
//        final ClickBean planClickBean = new ClickBean()
//                .setUrl(URLConfig.HOME_SUPPORT_PLAN_URL)
//                .setTitle("小贝私人定制")
//                .showTitle()
//                .showShare();
//        final ClickBean medicalClickBean = new ClickBean()
//                .setUrl(URLConfig.HOME_SUPPORT_MEDICAL_URL)
//                .setTitle("合规医诊")
//                .showTitle()
//                .showShare();
//        rlTwoLeft.setOnClickListener(new View.OnClickListener() {
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_SUPPORT_PLAN)
//                        .setContext(contextName)
//                        .setUrl(URLConfig.HOME_SUPPORT_PLAN_URL)
//                        .send();
//
//                HyUtils.startHyActivity(context, planClickBean);
//            }
//        });
//        rlTwoRight.setOnClickListener(new View.OnClickListener() {
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_MEDICAL)
//                        .setContext(contextName)
//                        .setUrl(URLConfig.HOME_SUPPORT_MEDICAL_URL)
//                        .send();
//
//                HyUtils.startHyActivity(context, medicalClickBean);
//            }
//        });
//    }
//
//    private void initOneBroad() {
//        //小贝测评
//        rlOneBroad = fragmentLayout.findViewById(R.id.layout_evaluation);
//        final ClickBean surveyClickBean = new ClickBean()
//                .setUrl(URLConfig.HOME_SURVEY_AI_URL)
//                .setTitle("小贝保险测评")
//                .showTitle()
//                .showShare();
//        rlOneBroad.setOnClickListener(new View.OnClickListener() {
//
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_SURVEY_AI)
//                        .setContext(contextName)
//                        .setUrl(URLConfig.HOME_SURVEY_AI_URL)
//                        .send();
//
//                HyUtils.startHyActivity(context, surveyClickBean);
//            }
//        });
//    }
//
//    private void initBanner() {
//        //banner
//        bannerDefault = fragmentLayout.findViewById(R.id.banner_default);
//        banner = fragmentLayout.findViewById(R.id.banner_home);
//    }
//
//    /**
//     * 注册事件回调
//     */
//    private void registerObserve() {
//        LiveDataBus.get().with(TAG_CLICK_HOME_STICKY_TITLE, Integer.class).observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(@Nullable Integer position) {
//                if (position != currentPage) {
//                    EventManager.EventSender.create(String.format(EventName.HOME_EVENT_ARTICLE_TAB, articleNameList.get(position)))
//                            .setContext(contextName)
//                            .send();
//
//                    clickTitle(position, false);
//                }
//            }
//        });
//
//        LiveDataBus.get().with(TAG_CLICK_HOME_BLANK).observe(this, new Observer<Object>() {
//            @Override
//            public void onChanged(@Nullable Object o) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_ARTICLE_MORE)
//                        .setContext(contextName)
//                        .put("currentPage", currentPage)
//                        .put("loadType", "manual")
//                        .send();
//                Rect rect = new Rect();
//                fragmentLayout.getWindowVisibleDisplayFrame(rect);
//                itemBlank.notifyData("加载中...", rect);
//                homeAdapter.notifyDataSetChanged();
//                refreshLayout.setEnableLoadMore(true);
//                requestHomeList();
//            }
//        });
//    }
//
//    private void initDebug() {
//        TextView debug = fragmentLayout.findViewById(R.id.debug_button);
//        if (!DollyApplication.isRelease()) {
//            debug.setVisibility(View.VISIBLE);
//        }
//        debug.setOnClickListener(new View.OnClickListener() {
//
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                ConfigActivity.start(context);
//            }
//        });
//    }
//
//    private void registerBroadcast() {
//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                if (action == null) {
//                    return;
//                }
//                switch (action) {
//                    case DollyUtils.ACTION_REFRESH_HOMEFRAGMENT:
//                        homeRecyclerView.smoothScrollToPosition(0);
//                        refreshLayout.autoRefresh();
//                }
//            }
//        };
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(DollyUtils.ACTION_REFRESH_HOMEFRAGMENT);
//        context.registerReceiver(broadcastReceiver, intentFilter);
//    }
//
//    public void loadArticleFailure() {
//        if (cacheArticleList.get(currentPage).isEmpty()) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (homeArticleBeans.get(homeArticleBeans.size() - 1).getViewType() != TYPE_HOME_FRAGMENT_BLANK) {
//                        homeArticleBeans.add(new ArticleSimpleBean(TYPE_HOME_FRAGMENT_BLANK));
//                    }
//                    Rect rect = new Rect();
//                    fragmentLayout.getWindowVisibleDisplayFrame(rect);
//                    itemBlank.notifyData("貌似网络不太稳定，请点击刷新", rect);
//                    homeAdapter.notifyDataSetChanged();
//                    refreshLayout.finishLoadMore(true);//结束加载
//                    refreshLayout.setEnableLoadMore(false);
//                }
//            });
//        }
//    }
//
//    public void removeBlankView() {
//        if (homeArticleBeans.get(homeArticleBeans.size() - 1).getViewType() == TYPE_HOME_FRAGMENT_BLANK) {
//            homeArticleBeans.remove(homeArticleBeans.size() - 1);
//        }
//    }
//
//    public void showBlankView() {
//        if (homeArticleBeans.get(homeArticleBeans.size() - 1).getViewType() != TYPE_HOME_FRAGMENT_BLANK) {
//            homeArticleBeans.add(new ArticleSimpleBean(TYPE_HOME_FRAGMENT_BLANK));
//            refreshLayout.finishLoadMore(true);//结束加载
//            refreshLayout.setEnableLoadMore(false);
//        }
//    }
//
//    private void requestArticle(final int typePage, final int page, final boolean isLoadData) {
//        requestArticle(typePage, page, isLoadData, false);
//    }
//
//    /**
//     * @param typePage   请求第几页的数据类型（热门、重疾、寿险、医疗、意外）
//     * @param page       请求的页数
//     * @param isLoadData 请求的数据是否加载到显示页
//     */
//    private void requestArticle(final int typePage, final int page, final boolean isLoadData, final boolean isRefresh) {
//        final JSONObject requestBody = new JSONObject();
//        requestBody.put("bean", requestTypeList.get(typePage));
//        requestBody.put("pageIndex", page);
//        requestBody.put("pageSize", 10);
//        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                "请求数据_首页_独家测评", new BehaviorLog.LogBuilder()
//                        .add("typePage", typePage)
//                        .add("page", page)
//                        .add("isLoadData", isLoadData)
//                        .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                        .add(BehaviorLog.BODY, requestBody)
//        );
//
//        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
//        formBody.add("bean", requestTypeList.get(typePage));//传递键值对参数
//        formBody.add("pageIndex", page + "");//传递键值对参数
//        formBody.add("pageSize", 10 + "");//传递键值对参数
//        DollyNetworkManager.startRequest(URLConfig.HOME_ARTICLE_URL, formBody, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                DLog.d("requestArticle", e.getMessage());
//                refreshLayout.finishLoadMore(false);//结束加载（加载失败）
//                BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                        "数据返回_首页_独家测评，请求失败", new BehaviorLog.LogBuilder()
//                                .add(BehaviorLog.ERROR, e.getMessage())
//                                .add("typePage", typePage)
//                                .add("page", page)
//                                .add("isLoadData", isLoadData)
//                                .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                .add(BehaviorLog.BODY, requestBody)
//                );
//                if (typePage == currentPage) {
//                    loadArticleFailure();
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                try {
//                    String responseData = response.body().string();
//                    JSONObject jsonObject = JSONObject.parseObject(responseData);
//                    if (jsonObject == null) {
//                        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                                "数据返回_首页_独家测评，数据异常，基础对象为空", new BehaviorLog.LogBuilder()
//                                        .add(BehaviorLog.BASE, "null")
//                                        .add("typePage", typePage)
//                                        .add("page", page)
//                                        .add("isLoadData", isLoadData)
//                                        .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                        .add(BehaviorLog.BODY, requestBody)
//                        );
//                        loadArticleFailure();
//                        return;
//                    }
//                    JSONObject dataObject = jsonObject.getJSONObject("data");
//                    if (dataObject == null) {
//                        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                                "数据返回_首页_独家测评，数据异常，data对象为空", new BehaviorLog.LogBuilder()
//                                        .add(BehaviorLog.BASE, jsonObject)
//                                        .add("typePage", typePage)
//                                        .add("page", page)
//                                        .add("isLoadData", isLoadData)
//                                        .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                        .add(BehaviorLog.BODY, requestBody)
//                        );
//                        loadArticleFailure();
//                        return;
//                    }
//                    JSONArray jsonArray = dataObject.getJSONArray("data");
//                    if (jsonArray == null) {
//                        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                                "数据返回_首页_独家测评，数据异常，data数组为空", new BehaviorLog.LogBuilder()
//                                        .add(BehaviorLog.BASE, jsonObject)
//                                        .add("typePage", typePage)
//                                        .add("page", page)
//                                        .add("isLoadData", isLoadData)
//                                        .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                        .add(BehaviorLog.BODY, requestBody)
//                        );
//                        loadArticleFailure();
//                        return;
//                    }
//                    final ArrayList<ArticleSimpleBean> data = new ArrayList<>();
//                    final int jsonArraySize = jsonArray.size();
//                    //没有数据获取，判定为无更多
//                    if (jsonArraySize == 0) {
//                        requestPageList[typePage] = -1;
//                        refreshLayout.finishLoadMoreWithNoMoreData();//结束加载更多，显示没有更多的文案
//                        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                                "数据返回_首页_独家测评，没有更多数据可加载", new BehaviorLog.LogBuilder()
//                                        .add(BehaviorLog.BASE, jsonObject)
//                                        .add("requestPageList", -1)
//                                        .add("typePage", typePage)
//                                        .add("page", page)
//                                        .add("isLoadData", isLoadData)
//                                        .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                        .add(BehaviorLog.BODY, requestBody)
//                        );
//                        return;
//                    }
//                    for (int i = 0; i < jsonArraySize; i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        ArticleSimpleBean bean = new ArticleSimpleBean(TYPE_HOME_FRAGMENT_SIMPLE_ARTICLE);
//                        bean.title = object.getString("title");
//                        bean.content = object.getString("description");
//                        bean.imgUrl = object.getString("imgUrl");
//                        bean.pageUrl = object.getString("pageUrl");
//                        bean.id = object.getString("evaluationId");
//                        bean.clickBean = new ClickBean()
//                                .setUrl(bean.pageUrl)
//                                .setTitle("测评详情")
//                                .showTitle()
//                                .setShareTitle("测评|" + bean.title)
//                                .setShareContent(bean.content)
//                                .setShareImgUrl(bean.imgUrl)
//                                .setShareJumpUrl(bean.pageUrl)
//                                .showShare()
//                                .setFavorId(bean.id)
//                                .setFavorType("EVALUATION")
//                                .showFavor()
//                        ;
//                        data.add(bean);
//                    }
//                    if (isRefresh) {
//                        cacheArticleList.get(typePage).clear();
//                    }
//                    cacheArticleList.get(typePage).addAll(data);
//                    requestPageList[typePage]++;//增加请求页
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_独家测评，成功加载", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.BASE, jsonObject)
//                                    .add("requestPageList", requestPageList[typePage])
//                                    .add("typePage", typePage)
//                                    .add("page", page)
//                                    .add("isLoadData", isLoadData)
//                                    .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                    .add(BehaviorLog.BODY, requestBody)
//                    );
//                    if (isLoadData) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                removeBlankView();
//                                for (int i = 0; i < jsonArraySize; i++) {
//                                    homeArticleBeans.add(data.get(i));
//                                }
//                                homeAdapter.notifyDataSetChanged();
//                                refreshLayout.setEnableLoadMore(true);
//                                refreshLayout.finishLoadMore(true);//结束加载
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    DLog.e("requestArticle", e.getMessage());
//                    refreshLayout.finishLoadMore(false);//结束加载
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_独家测评，数据解析崩溃", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.ERROR, e.getMessage())
//                                    .add("requestPageList", requestPageList[typePage])
//                                    .add("typePage", typePage)
//                                    .add("page", page)
//                                    .add("isLoadData", isLoadData)
//                                    .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                    .add(BehaviorLog.BODY, requestBody)
//                    );
//                }
//            }
//        });
//    }
//
//    private void initSearch() {
//        searchTextView = fragmentLayout.findViewById(R.id.text_search);
//        searchTextView.setOnClickListener(new View.OnClickListener() {
//
//            @SingleClick
//            @Override
//            public void onClick(View v) {
//                EventManager.EventSender.create(EventName.HOME_EVENT_SEARCH)
//                        .setContext(contextName)
//                        .put("hint_text", "searchHintText")
//                        .send();
//
//                Intent intent = new Intent(context, SearchActivity.class);
//                intent.putExtra("hint_text", searchHintText);
//                startActivity(intent);
//            }
//        });
//        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                "请求数据_首页_大家都在搜", new BehaviorLog.LogBuilder()
//                        .add(BehaviorLog.URL, URLConfig.EVERYBODY_SEARCH_URL)
//        );
//
//        DollyNetworkManager.startRequest(URLConfig.EVERYBODY_SEARCH_URL, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                DLog.d("everybodySearching", e.getMessage());
//                BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                        "数据返回_首页_大家都在搜，请求失败", new BehaviorLog.LogBuilder()
//                                .add(BehaviorLog.URL, URLConfig.EVERYBODY_SEARCH_URL)
//                                .add(BehaviorLog.ERROR, e.getMessage())
//                );
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                try {
//                    JSONObject jsonObject = JSONObject.parseObject(response.body().string());
//                    if (jsonObject == null) {
//                        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                                "数据返回_首页_大家都在搜，数据异常，基础对象为空", new BehaviorLog.LogBuilder()
//                                        .add(BehaviorLog.URL, URLConfig.EVERYBODY_SEARCH_URL)
//                                        .add(BehaviorLog.BASE, "null")
//                        );
//                        return;
//                    }
//                    searchHintText = jsonObject.getString("data");
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_大家都在搜，加载成功", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.URL, URLConfig.EVERYBODY_SEARCH_URL)
//                                    .add(BehaviorLog.BASE, jsonObject)
//                    );
//                    if (searchHintText != null && !searchHintText.equals("")) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                searchHintText = "大家都在搜：" + searchHintText;
//                                searchTextView.setHint(searchHintText);
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    DLog.e("everybodySearching", e.getMessage());
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_大家都在搜，数据解析崩溃", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.URL, URLConfig.EVERYBODY_SEARCH_URL)
//                                    .add(BehaviorLog.ERROR, e.getMessage())
//                    );
//                }
//            }
//        });
//    }
//
//    /**
//     * 点击字体变大，出现下红线，刷新独家测评的recyclerView
//     *
//     * @param position 点击的位置
//     */
//    public void clickTitle(final int position, final boolean scroll) {
//        if (position < 0 || position > stickTitleList.size()) {
//            return;
//        }
//        itemArticleTitle.notifyData(position);
//        homeAdapter.notifyDataSetChanged();
//        for (int i = 0; i < stickTitleList.size(); i++) {
//            if (i == position) {
//                stickTitleList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//                stickTitleList.get(i).setTypeface(Typeface.DEFAULT_BOLD);
//                stickTitleList.get(i).setTextColor(getResources().getColor(R.color.black_333333));
//                stickMarkList.get(i).setVisibility(View.VISIBLE);
//            } else {
//                stickTitleList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
//                stickTitleList.get(i).setTypeface(Typeface.DEFAULT);
//                stickTitleList.get(i).setTextColor(getResources().getColor(R.color.gray_666666));
//                stickMarkList.get(i).setVisibility(View.INVISIBLE);
//            }
//        }
//        //恢复没有更多数据的原始状态
//        refreshLayout.setNoMoreData(false);
//        currentPage = position;
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                DLog.d("=====>", "cur thread = " + Thread.currentThread().getName());
//                //清除现有的独家测评数据，加载缓存的跳转后页面数据
//                for (int i = homeArticleBeans.size() - 1; i > 0; i--) {
//                    if (homeArticleBeans.get(i).getViewType() == TYPE_HOME_FRAGMENT_SIMPLE_ARTICLE || homeArticleBeans.get(i).getViewType() == TYPE_HOME_FRAGMENT_BLANK) {
//                        homeArticleBeans.remove(i);
//                    }
//                }
//                homeArticleBeans.addAll(cacheArticleList.get(position));
//                final int size = cacheArticleList.get(position).size();
//                if (size == 0) {
//                    //size为0，展示blankView
//                    showBlankView();
//                    requestArticle(currentPage, 0, true);
//                }
//                homeAdapter.notifyDataSetChanged();
//                if (scroll) {
//                    if (positionList[position] > stickPosition) {
////                        smoothScroller.setTargetPosition(positionList[position]);
////                        homeLayoutManager.startSmoothScroll(smoothScroller);
//                        homeLayoutManager.scrollToPosition(positionList[position]);
//                    } else {
////                        smoothScroller.setTargetPosition(POSITION_ARTICLE_FIRST);
////                        homeLayoutManager.startSmoothScroll(smoothScroller);
//                        homeLayoutManager.scrollToPosition(stickPosition);
//                    }
//                }
//            }
//        });
//    }
//
//    private void initData() {
//        requestTypeList = new ArrayList<>();
//        requestTypeList.add("evaluation_hot");//热门
//        requestTypeList.add("serious_illness");//重疾
//        requestTypeList.add("life_insurance");//寿险
//        requestTypeList.add("medical");//医疗
//        requestTypeList.add("accident");//意外
//
//        articleNameList = new ArrayList<>();
//        articleNameList.add("热门");
//        articleNameList.add("重疾");
//        articleNameList.add("寿险");
//        articleNameList.add("医疗");
//        articleNameList.add("意外");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        overGuideThread = true;//结束文案轮播的线程
//        if (broadcastReceiver != null) {
//            context.unregisterReceiver(broadcastReceiver);
//            broadcastReceiver = null;
//        }
//    }
//
//    /**
//     * 初始化主recyclerView
//     */
//    private void initRecyclerView() {
//        //设置为垂直布局，这也是默认的
//        homeLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        //吸顶监听
//        homeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            private boolean isFromTop = true;
//            private int lastPosition = 0;
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int position = homeLayoutManager.findFirstVisibleItemPosition();
//                positionList[currentPage] = position;
//                if (position > lastPosition) {
//                    isFromTop = true;
//                } else if (position < lastPosition) {
//                    isFromTop = false;
//                }
//                lastPosition = position;
//            }
//        });
//        //设置增加或删除条目的动画
//        homeRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        homeRecyclerView.scrollToPosition(0);
//    }
//
//    @Override
//    public RefreshHelper createRefreshHelper() {
//        refreshLayout = fragmentLayout.findViewById(R.id.refreshLayout);
//        return RefreshHelper.createRefreshHelper(refreshLayout);
//    }
//
//    @Override
//    public RecyclerView createRecyclerView() {
//        homeRecyclerView = fragmentLayout.findViewById(R.id.list_home);
//        return homeRecyclerView;
//    }
//
//    @Override
//    public RecyclerView.LayoutManager createLayoutManager() {
//        homeLayoutManager = new LinearLayoutManager(context);
//        return homeLayoutManager;
//    }
//
//    @Override
//    public RViewAdapter<ArticleSimpleBean> createRecyclerViewAdapter() {
//        homeArticleBeans = new ArrayList<>();
//        homeAdapter = new RViewAdapter<>(homeArticleBeans);
//        homeAdapter.addItemStyles(new ItemSimpleArticle(context, contextName));
//        return homeAdapter;
//    }
//
//    @Override
//    public void onRefresh() {
//        refreshLayout.finishRefresh(5000, false);
//        requestBanner(true);
//        requestHomeList();
//
//        EventManager.EventSender.create(EventName.HOME_EVENT_REFRESH)
//                .setContext(contextName)
//                .put("currentPage", currentPage)
//                .send();
//    }
//
//    @Override
//    public void onLoadMore() {
//        if (requestPageList[currentPage] == -1) {
//            refreshLayout.finishLoadMoreWithNoMoreData();
//        } else {
//            refreshLayout.finishLoadMore(5000, false, false);//最多加载时间为5秒
//            //请求更多数据
//            requestHomeList();
//        }
//
//        EventManager.EventSender.create(EventName.HOME_EVENT_ARTICLE_MORE)
//                .setContext(contextName)
//                .put("currentPage", currentPage)
//                .put("loadType", "auto")
//                .send();
//    }
//
//    /**
//     * 请求首页列表数据
//     */
//    private void requestHomeList() {
//        final HashMap<String, String> params = new HashMap<>();
//        params.put("bean", types[currentPosition]);
//        params.put("pageIndex", String.valueOf(page));
//        params.put("pageSize", "10");
//
//        final String requestString = JSONObject.toJSONString(params);
//        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                "请求数据_首页_独家测评", new BehaviorLog.LogBuilder()
//                        .add("typePage", currentPosition)
//                        .add("page", page)
//                        .add("isLoadData", true)
//                        .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                        .add(BehaviorLog.BODY, requestString)
//        );
//
//        HttpHelper.getInstance().post(URLConfig.HOME_ARTICLE_URL, params, new ICallback() {
//            @Override
//            public void onFailure(int code, String errorMsg) {
//                BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                        "数据返回_首页_独家测评，请求失败", new BehaviorLog.LogBuilder()
//                                .add(BehaviorLog.ERROR, errorMsg)
//                                .add("typePage", currentPosition)
//                                .add("page", page)
//                                .add("isLoadData", true)
//                                .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                .add(BehaviorLog.BODY, requestString)
//                );
//
//                //显示没有数据页面
//                if (page == 0) {
//                    //TODO 显示一个加载失败的页面
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvEmptyView.setVisibility(View.VISIBLE);
//                            homeRecyclerView.setVisibility(View.GONE);
//                            refreshLayout.setEnableLoadMore(false);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onSuccess(String model) {
//                JSONObject jsonObject = JSONObject.parseObject(model);
//                if (jsonObject == null) {
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_独家测评，数据异常，基础对象为空", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.BASE, "null")
//                                    .add("typePage", currentPosition)
//                                    .add("page", page)
//                                    .add("isLoadData", true)
//                                    .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                    .add(BehaviorLog.BODY, requestString)
//                    );
////                    loadArticleFailure();
//                    return;
//                }
//                JSONObject dataObject = jsonObject.getJSONObject("data");
//                if (dataObject == null) {
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_独家测评，数据异常，data对象为空", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.BASE, jsonObject)
//                                    .add("typePage", currentPosition)
//                                    .add("page", page)
//                                    .add("isLoadData", true)
//                                    .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                    .add(BehaviorLog.BODY, requestString)
//                    );
////                    loadArticleFailure();
//                    return;
//                }
//                JSONArray jsonArray = dataObject.getJSONArray("data");
//                if (jsonArray == null) {
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_独家测评，数据异常，data数组为空", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.BASE, jsonObject)
//                                    .add("typePage", currentPosition)
//                                    .add("page", page)
//                                    .add("isLoadData", true)
//                                    .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                    .add(BehaviorLog.BODY, requestString)
//                    );
////                    loadArticleFailure();
//                    return;
//                }
//                if (jsonArray.size() == 0) {
//                    if(page == 0){
//                        tvEmptyView.setVisibility(View.VISIBLE);
//                        homeRecyclerView.setVisibility(View.GONE);
//                    }else {
//                        tvEmptyView.setVisibility(View.GONE);
//                        homeRecyclerView.setVisibility(View.VISIBLE);
//                        refreshLayout.finishLoadMoreWithNoMoreData();//结束加载更多，显示没有更多的文案
//                    }
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_独家测评，没有更多数据可加载", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.BASE, jsonObject)
//                                    .add("requestPageList", -1)
//                                    .add("typePage", currentPosition)
//                                    .add("page", page)
//                                    .add("isLoadData", true)
//                                    .add(BehaviorLog.URL, URLConfig.HOME_ARTICLE_URL)
//                                    .add(BehaviorLog.BODY, requestString)
//                    );
//                    return;
//                }
//                Gson gson = new Gson();
//                final List<ArticleSimpleBean> articles = gson.fromJson(jsonArray.toJSONString(), new TypeToken<List<ArticleSimpleBean>>() {
//                }.getType());
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //说明是刷新
//                        if (page == 0) {
//                            homeArticleBeans.clear();
//                        }
//                        page++;
//                        homeArticleBeans.addAll(articles);
//                        homeAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 请求banner数据
//     *
//     * @param isRefresh
//     */
//    private void requestBanner(final boolean isRefresh) {
//        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                "请求数据_首页_banner", new BehaviorLog.LogBuilder()
//                        .add(BehaviorLog.URL, URLConfig.BANNER_URL)
//                        .add("isRefresh", isRefresh)
//        );
//        DollyNetworkManager.startRequest(URLConfig.BANNER_URL, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                DLog.e("requestBanner", e.getMessage());
//                if (isRefresh) {
//                    refreshLayout.finishRefresh(false);
//                }
//                BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                        "数据返回_首页_banner，请求失败", new BehaviorLog.LogBuilder()
//                                .add(BehaviorLog.ERROR, e.getMessage())
//                                .add(BehaviorLog.URL, URLConfig.BANNER_URL)
//                                .add("isRefresh", isRefresh));
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                try {
//                    String responseData = response.body().string();
//                    HyWebSynCookieUtil.setCookie(response.headers());
//                    JSONObject jsonObject = JSONObject.parseObject(responseData);
//                    if (jsonObject == null) {
//                        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                                "数据返回_首页_banner，数据异常，基础对象为空", new BehaviorLog.LogBuilder()
//                                        .add(BehaviorLog.BASE, "null")
//                                        .add(BehaviorLog.URL, URLConfig.BANNER_URL)
//                                        .add("isRefresh", isRefresh));
//                        return;
//                    }
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//                    if (jsonArray == null) {
//                        BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                                "数据返回_首页_banner，数据异常，data数组为空", new BehaviorLog.LogBuilder()
//                                        .add(BehaviorLog.BASE, jsonObject)
//                                        .add(BehaviorLog.URL, URLConfig.BANNER_URL)
//                                        .add("isRefresh", isRefresh));
//                        return;
//                    }
//                    imgArrayList.clear();
//                    urlArrayList.clear();
//                    for (int i = 0; i < jsonArray.size(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        if (object.getString("position").equals("HOME_PAGE")) {
//                            imgArrayList.add(object.getString("imgUrl"));
//                            urlArrayList.add(new ClickBean()
//                                    .setUrl(object.getString("url"))
//                                    .showTitle());
//                        }
//                    }
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_banner，成功加载", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.BASE, jsonObject)
//                                    .add(BehaviorLog.URL, URLConfig.BANNER_URL)
//                                    .add("isRefresh", isRefresh));
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            setBannerData();
//                            if (isRefresh) {
//                                refreshLayout.finishRefresh(true);
//                            }
//                        }
//                    });
//                } catch (Exception e) {
//                    if (isRefresh) {
//                        refreshLayout.finishRefresh(false);
//                    }
//                    BehaviorLog.doMark(BehaviorLog.ACTION_REQUEST, contextName,
//                            "数据返回_首页_banner，数据解析崩溃", new BehaviorLog.LogBuilder()
//                                    .add(BehaviorLog.ERROR, e.getMessage())
//                                    .add(BehaviorLog.URL, URLConfig.BANNER_URL)
//                                    .add("isRefresh", isRefresh));
//                    DLog.e("requestUrl", e.getMessage());
//                }
//            }
//        });
//    }
//
//    /**
//     * 设置banner数据
//     */
//    private void setBannerData() {
//        if (!DollyUtils.isEmpty(imgArrayList) && !DollyUtils.isEmpty(urlArrayList)) {
//            bannerDefault.setVisibility(View.GONE);
//            banner.setVisibility(View.VISIBLE);
//            banner.setImageLoader(new GlideImageLoader());
//            banner.setImages(imgArrayList);
//            banner.setOnBannerListener(new OnBannerListener() {
//                @Override
//                public void OnBannerClick(int position) {
//                    if (urlArrayList.size() - 1 >= position) {
//                        EventManager.EventSender.create(EventName.HOME_EVENT_BANNER)
//                                .setContext(contextName)
//                                .send();
//
//                        HyUtils.startHyActivity(context, urlArrayList.get(position));
//                    }
//                }
//            });
//            //设置轮播时间
//            banner.setDelayTime(3000);
//            banner.start();
//        } else {
//            bannerDefault.setVisibility(View.VISIBLE);
//            banner.setVisibility(View.GONE);
//        }
//    }
//}