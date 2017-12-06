package deliver.aihui.com.dcdeliver.base;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/3/23 11:57
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/3/23$
 * @ 更新描述  ${TODO}
 */

public class Content {
    public static final String IS_REMEMBER_PW = "isrememberpw";     //是否记住密码
    public static final String UnDownDatas    = "undowndatas";     //没有下载的数据 用于sp存储

    public static final String PASSWORD     = "password";             //密码
    public static final String USER_ACCOUNT = "user_account";      //账号
    public static final String WS_ADDRESS   = "wsAddress";      //账号

    //资产清点配置
    public static final int REQUEST_CONFIG        = 1;
    //照片的
    public static final int REQUEST_PIC_CHOOSE    = 2;
    //相机的
    public static final int SCANNIN_GREQUEST_CODE = 3;
    //选公司的
    public static final int COMPANY_REQUEST_CODE  = 4;

    //用户
    public static final int SYSUSER_REQUEST_CODE = 5;

    //巡检人员
    public static final int XJ_USER_REQUEST_CODE = 6;

    //培训管理
    public static final int PXGL_PXF_REQUEST_CODE  = 7;
    public static final int PXGL_PXRY_REQUEST_CODE = 8;
    //培训管理设备
    public static final int PXGL_WZ_REQUEST_CODE   = 9;

    //安装验收列表
    public static final int AZYS_LIST_REQUEST_CODE = 10;

    //备注
    public static final int BZ_REQUEST_CODE = 11;

    //配件明细
    public static final int PJMX_REQUEST_CODE = 12;

    //预转科
    public static final int YZK_REQUEST_CODE         = 13;
    //培训管理明细
    public static final int PXGL_DETAIL_REQUEST_CODE = 14;

    //文件选择器
    public static final int FILE_PICK_REQUEST_CODE = 15;

    //跳往巡检明细一级页面
    public static final int INSPECT_DETAIL_REQUESET_CODE = 16;

    //跳往巡检明细二级页面
    public static final int INSPECT_DETAIL2_REQUESET_CODE = 17;

    //巡检配置
    public static final int INSPECT_CONFIG_REQUESET_CODE = 18;

    //开始巡检
    public static final int INSPECT_START_REQUESET_CODE = 19;
    //品牌名称
    public static final int PPMC_REQUEST_CODE           = 20;
    //编辑
    public static final int ASSET_EDIT_REQUEST_CODE     = 21;

    //安装验收明细
    public static final int AZYS_DETAIL_REQUEST_CODE = 22;

    //验收人
    public static final int AZYS_YSR_REQUEST_CODE = 23;

    //homeFragment 跳转
    public static final int HOME_REQUEST_CODE = 23;


    //供货单位1 生产厂家2 保修单位3
    public static final int COMPANY_GHDW = 1;
    public static final int COMPANY_SCCJ = 2;
    public static final int COMPANY_BXDW = 3;


    /**
     * 跳往选取供货单位的名称
     */
    public static final String COMPANY_TYPE = "companyType";
    public static final String COMPANY_ID   = "companyId";


    //那个跳往配置界面的
    public static final int    AZYS_CONFIG          = 1;
    public static final int    INSPECT_REQUEST_CODE = 2;
    public static final int    ASSET_QUERY          = 3;
    public static final int    ASSET_CHECK          = 4;
    public static final String INSPECT_PNAME        = "inspect_pname";
    public static final String ISREMEMBERPW         = "isrememberpw";


    /**
     * 是否是多选
     */
    public static String IS_MULTISELECT = "IS_MULTISELECT";

    /**
     * 选择人物返回的id的集合
     */
    public static String CHECKED_USER_IDS   = "checkedUser";
    public static String CHECKED_USER_NAMES = "checkedUserNames";
    public static String USER_ID_LIST       = "userIdList";
    public static String USER_NAME_LIST     = "userNameList";


    /**
     * 设备
     */
    public static String CHECKED_SB_IDS   = "checkedSb";
    public static String CHECKED_SB_NAMES = "checkedSbNames";


    public static String AZYS_DETAIL_IDS = "azys_detail_ids";


    public static String PPMC_IDS   = "ppmc_ids";
    public static String PPMC_NAMES = "ppmc_names";
    /**
     * 设备的ID的集合
     */
    public static String SB_IDS     = "sb_ids";


    public static String FJ_PATH = "paths";

    //成功
    public static       int WEB_RESP_CODE_SUCCESS = 1;
    //token
    public static final int TOKEN_EXPRIED         = 2;

}
