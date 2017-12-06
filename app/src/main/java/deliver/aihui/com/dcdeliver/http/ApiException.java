package deliver.aihui.com.dcdeliver.http;


import deliver.aihui.com.dcdeliver.base.Content;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/8/25 15:00
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/8/25$
 * @ 更新描述  ${TODO}
 */

public class ApiException extends RuntimeException {
    private int mErrorCode;

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    public boolean isTokenExpried() {
        return mErrorCode == Content.TOKEN_EXPRIED;
    }
}


