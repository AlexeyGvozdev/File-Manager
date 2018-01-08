package com.example.alexey.filemanager

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.os.Build
import org.jetbrains.anko.toast


open class MainActivity : AppCompatActivity() {

    val NUMBER_OF_REQUEST = 23401
    private var pathName: String = "/sdcard/"
    val adapter = MyAdapter{ clickListener(it) }

    private fun clickListener(it: FileClass) {
        pathName += "${it.nameFile}/"
        adapter.setListElements(showFolder(pathName))
    }

    val TAG = "MY TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycle_view.adapter = adapter
        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager = LinearLayoutManager(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val canRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            val canWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (canRead != PackageManager.PERMISSION_GRANTED || canWrite != PackageManager.PERMISSION_GRANTED) {

                //Нужно ли нам показывать объяснения , зачем нам нужно это разрешение
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //показываем объяснение
                } else {
                    //просим разрешение
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), NUMBER_OF_REQUEST)
                }
            } else {
                adapter.setListElements(showFolder(pathName))
                //ваш код
            }
        }

    }

    var fullPathName: String = ""
    private val listMusicExtension =  arrayOf("mp3", "flac", "la", "ogg","wav")
    var listFiles: ArrayList<FileClass> = ArrayList()

    fun showFolder(pathName: String): ArrayList<FileClass> {
        listFiles = ArrayList()

        val file = File(pathName)
        val tag = "My tag"
        if(!file.isFile) {
            Log.d(tag, pathName)
            val f: Array<File> = file.listFiles()
            var isFolder: Boolean
            for (x in f) {
                isFolder = !x.isFile

                if (x.name[0] != '.') {
//                        listFiles.add(FileClass(x.name, isFolder))
                    if (isFolder || listMusicExtension.indexOf(x.extension) != -1) listFiles.add(FileClass(x.name, isFolder))
//                        else if (listMusicExtension.indexOf(x.extension) != -1) listFiles.add(FileClass(x.name, isFolder))
                }
            }
        } else {
            toast("Вы выбрали: ${getNameElements(pathName)}")
//                Toast.makeText(this, getNameElements(fullPathName), Toast.LENGTH_LONG).show()
        }
        return listFiles
    }

    private fun getNameElements(pathName: String): String {
        val position: Int = pathName.lastIndexOf('/', pathName.length - 2)
        val name: String = pathName.substring(position + 1, pathName.length - 1)
        return name
    }

    override fun onBackPressed() {
        Log.d(TAG, pathName)
        val position: Int = pathName.lastIndexOf('/', pathName.length - 2)
        pathName = pathName.substring(0, position + 1)
        Log.d(TAG, pathName)
        adapter.setListElements(showFolder(pathName))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            NUMBER_OF_REQUEST -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("TAG", "Пользователь дал разрешение")
                } else {
                    Log.e("TAG", "Пользователь отклонил разрешение")
                }
                return
            }
        }
    }
}

