package com.jiangkang.ktools.widget

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.ButterKnife
import com.jiangkang.ktools.R
import com.jiangkang.tools.utils.FileUtils
import com.jiangkang.tools.utils.ToastUtils
import com.jiangkang.tools.widget.KDialog
import kotlinx.android.synthetic.main.activity_kdialog.*
import java.text.DateFormat
import java.util.*

class KDialogActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kdialog)
        ButterKnife.bind(this)

        handleClick()

    }

    private fun handleClick() {

        btnCreateSimpleTextDialog.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle("AlertDialog")
                    .setMessage("AlertDialog.Builder(context)\n     .setMessage('....')\n     .show()")
                    .setNegativeButton("关闭") { dialog, which -> dialog.dismiss() }
                    .setCancelable(false)
                    .show()
        }


        btn_single_choice_dialog.setOnClickListener {
            val singleChoiceItems = arrayOf("篮球", "足球", "乒乓球", "羽毛球")
            KDialog.showSingleChoiceDialog(this,
                    "你最喜欢哪种运动？",
                    singleChoiceItems
            ) { index -> ToastUtils.showShortToast(singleChoiceItems[index]) }
        }


        btn_multi_choices_dialog.setOnClickListener {
            val multiChoicesItems = arrayOf("篮球", "足球", "乒乓球", "羽毛球")
            KDialog.showMultiChoicesDialog(
                    this,
                    "你擅长哪些运动？",
                    multiChoicesItems,
                    object : KDialog.MultiSelectedCallback {
                        override fun multiSelected(list: List<Int>) {
                            val builder = StringBuilder()
                            for (index in list) {
                                builder.append("\n" + multiChoicesItems[index])
                            }
                            ToastUtils.showShortToast("你擅长:" + builder.toString())
                        }

                        override fun selectedNothing() {
                            ToastUtils.showShortToast("你是一个不爱运动的人")
                        }
                    }
            )
        }


        btn_progress_dialog_simple.setOnClickListener {
            val progress = intArrayOf(0)
            val dialog = ProgressDialog(this)
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    progress[0] += 10
                    dialog.progress = progress[0]
                    if (progress[0] >= 100) {
                        ToastUtils.showShortToast("加载完成")
                        timer.cancel()
                        dialog.dismiss()
                    }
                }
            }, 0, 500)
        }

        btn_progress_dialog_indeterminate.setOnClickListener {
            val dialog = ProgressDialog(this)
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setMessage("正在加载中，请稍等....")
            dialog.show()
        }

        btn_input_dialog.setOnClickListener {
            /*
       * 封装之后，会变得非常有趣
       * */
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL
            val inputView1 = LayoutInflater.from(this).inflate(R.layout.layout_input_dialog, linearLayout, false)
            val inputView2 = LayoutInflater.from(this).inflate(R.layout.layout_input_dialog, linearLayout, false)
            (inputView1.findViewById(R.id.tv_input) as TextView).text = "用户名："
            (inputView1.findViewById(R.id.et_input) as EditText).hint = "请输入用户名"
            (inputView2.findViewById(R.id.tv_input) as TextView).text = "密    码："
            (inputView2.findViewById(R.id.et_input) as EditText).hint = "请输入密码"
            linearLayout.addView(inputView1, 0)
            linearLayout.addView(inputView2, 1)
            AlertDialog.Builder(this)
                    .setView(linearLayout)
                    .setTitle("登录框")
                    .setCancelable(false)
                    .setPositiveButton("登录") { dialog, which ->
                        ToastUtils.showShortToast("这只是一个假登录")
                        dialog.dismiss()
                    }
                    .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                    .show()
        }

        btn_choose_date_dialog.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
                        ToastUtils.showShortToast(date)
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        btn_choose_time_dialog.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dialog = TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        val time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)
                        ToastUtils.showShortToast(time)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
            )
            dialog.show()
        }


        btn_web_view_dialog.setOnClickListener {
            val webView = WebView(this)
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(FileUtils.getAssetsPath("web/gravity-points/index.html"))
            AlertDialog.Builder(this)
                    .setView(webView)
                    .setNegativeButton("关闭") { dialog, which -> dialog.dismiss() }
                    .setCancelable(false)
                    .show()
        }


        btn_bottom_dialog.setOnClickListener {
            val webView = WebView(this)
            webView.loadUrl("https://github.com/jiangkang/KTools")
            webView.settings.javaScriptEnabled = true
            val dialog = BottomSheetDialog(this)
            dialog.setCancelable(true)
            dialog.setContentView(webView)
            dialog.show()
        }
    }


    companion object {

        fun launch(context: Context, bundle: Bundle?) {
            val intent = Intent(context, KDialogActivity::class.java)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }
}
