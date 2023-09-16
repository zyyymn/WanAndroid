package util

import android.app.Application
import android.util.Log
import okio.BufferedSink
import okio.Sink
import okio.appendingSink
import okio.buffer
import java.io.File
import java.util.concurrent.Executors


/**
 * log工具类
 * */
object AppLogUtil {

    private var mContext: Application? = null
    private var logFile: File? = null
    private var sink: Sink? = null
    private var bufferedSink: BufferedSink? = null

    //核心线程与最大线程数是 1
    private val logThread = Executors.newFixedThreadPool(1)

    //开启线程
    private fun runOnWorkrThread(runnable: Runnable?){
        logThread.execute(runnable)
    }

    fun init(context: Application){
        mContext =context
    }

    //io操作在其他线程
    fun addLifeLog(data: String){
        runOnWorkrThread(Runnable {
            try {
                Log.d("addFileLog","addLifeLog:1")
                val path = mContext!!.getExternalFilesDir(null).toString()+"/logs/"
                if (logFile == null){
                    logFile = File(path,"lifelog.tex")
                }
                if (!logFile!!.exists()){
                    Log.d("addlifelog","addLifeLog:2")
                    val dir = File(path)
                    if (dir.mkdir()){
                        if (logFile!!.createNewFile()){
                            Log.d("addLifeLog","addLifeLog:3")
                        }
                    }
                }

                Log.d("addLifeLog","addLifeLog:4")
                sink = logFile!!.appendingSink()
                bufferedSink = sink!!.buffer()
                bufferedSink!!.writeUtf8(System.currentTimeMillis().toString())
                bufferedSink!!.writeUtf8("-------------")
                bufferedSink!!.writeUtf8(data!!)
                bufferedSink!!.writeUtf8("\n")
                bufferedSink!!.flush()
                sink!!.close()

            }catch (e: Exception){
                throw RuntimeException(e)

            }
        })
    }



}