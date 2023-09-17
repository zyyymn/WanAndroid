package network


/**
 * 处理网络请求返回的数据
 * */
class BaseResp<T> {
    var errorCode: Int = -1
    var errorMsg: String = ""
    var data: T? = null
    var responseState: ResponseState? = null

    enum class ResponseState{
        REQUEST_START,
        REQUEST_SUCCESS,
        REQUEST_FAILED,
        REQUEST_ERROR
    }
}