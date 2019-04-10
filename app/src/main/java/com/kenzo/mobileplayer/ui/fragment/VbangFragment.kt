package com.kenzo.mobileplayer.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.AsyncTask
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import com.itheima.player.model.AudioBean
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.adapter.VbangAdapter
import com.kenzo.mobileplayer.base.BaseFragment
import com.kenzo.mobileplayer.ui.activity.AudioPlayerActivity
import com.kenzo.mobileplayer.util.CursorUtil
import kotlinx.android.synthetic.main.fragment_vbang.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton

class VbangFragment : BaseFragment() {
    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_vbang, null)
    }

    override fun initDate() {

        //动态权限申请
        handlePermission()


    }

    /**
     * 处理权限问题
     */
    private fun handlePermission() {
        val permissing = Manifest.permission.READ_EXTERNAL_STORAGE
        //查看是否有权限
        val checkSelfPermission = context?.let { ActivityCompat.checkSelfPermission(it, permissing) }
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            loadSongs()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity as Activity, permissing)) {
                alert("求求你啦  给个权限吧", "那个..") {
                    yesButton { myRequestPermission() }
                    noButton { }
                }.show()
            } else {
                myRequestPermission()
            }
        }
    }

    /**
     * 真正获取权限操作
     */
    private fun myRequestPermission() {
        val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(permission, 1)
    }

    /**
     * 接收权限授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSongs()
        }
    }

    private fun loadSongs() {
        val resolver = context?.contentResolver

        /*val cursor = resolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST
            ),
            null, null, null
        )
        CursorUtil.logCursor(cursor)*/

        //AudioTask().execute(resolver)

        val handler = object : AsyncQueryHandler(resolver) {
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                //CursorUtil.logCursor(cursor)
                (cookie as VbangAdapter).swapCursor(cursor)
            }

        }

        handler.startQuery(
            0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST
            ),
            null, null, null
        )
    }

    var adapter: VbangAdapter? = null

    override fun initListener() {
        adapter = VbangAdapter(context, null)
        listView.adapter = adapter

        //条目点击事件
        listView.setOnItemClickListener { parent, view, position, id ->
            //获取数据集合
            val cursor = adapter?.getItem(position) as Cursor
            //通过当前cursor获取整个数据集合
            val list: ArrayList<AudioBean> = AudioBean.getAudioBeans(cursor)
            //当前点击position
            //跳转音乐播放界面
            startActivity<AudioPlayerActivity>("list" to list, "position" to position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        adapter?.changeCursor(null)
    }


    class AudioTask : AsyncTask<ContentResolver, Void, Cursor>() {
        override fun doInBackground(vararg params: ContentResolver?): Cursor? {
            val cursor = params[0]?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.SIZE,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.ARTIST
                ),
                null, null, null
            )
            return cursor
        }

        override fun onPostExecute(result: Cursor?) {
            super.onPostExecute(result)
            CursorUtil.logCursor(result)
        }
    }
}