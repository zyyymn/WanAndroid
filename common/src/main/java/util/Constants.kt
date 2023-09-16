package util

class Constants {

    companion object{

        const val MIN_CLICK_DELAY_TIME: Int = 1000

        //ARouter
        const val WEB_TITLE: String = "web_title"
        const val WEB_LINK: String = "web_link"
        const val PATH_WEB: String = "web/ui/WebActivity"
        const val PATH_LOGIN: String = "user/ui/LoginActivity"

        //kv
        const val USER_NAME: String = "user_name"
        const val USER_COOKIE: String = "user_cookie"

        //http
        const val HTTP_SUCCESS =0
        const val HTTP_AUTH_INVALID = -1001
    }
}