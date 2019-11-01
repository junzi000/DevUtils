package dev.utils.app.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.IntRange;
import android.support.annotation.RawRes;
import android.support.v4.app.DialogFragment;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.io.Closeable;
import java.io.File;
import java.util.Locale;

import dev.utils.app.AnalysisRecordUtils;
import dev.utils.app.CleanUtils;
import dev.utils.app.ClickUtils;
import dev.utils.app.ClipboardUtils;
import dev.utils.app.ContentResolverUtils;
import dev.utils.app.DialogUtils;
import dev.utils.app.EditTextUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.KeyBoardUtils;
import dev.utils.app.LanguageUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.NotificationUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ScreenUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.VibrationUtils;
import dev.utils.app.assist.manager.TimerManager;
import dev.utils.app.image.BitmapUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.HttpURLConnectionUtils;
import dev.utils.common.assist.TimeKeeper;

/**
 * detail: Dev 工具类链式调用 Helper 类
 * @author Ttt
 * <pre>
 *     通过 DevApp 工具类快捷实现
 *     <p></p>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 * </pre>
 */
public final class DevHelper {

    // TimeKeeper
    private TimeKeeper mTimeKeeper = new TimeKeeper();
    // DevHelper
    private static final DevHelper HELPER = new DevHelper();

    /**
     * 获取单例 DevHelper
     * @return {@link DevHelper}
     */
    public static DevHelper get() {
        return HELPER;
    }

    // ==========
    // = Helper =
    // ==========

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    public ViewHelper viewHelper() {
        return ViewHelper.get();
    }

    /**
     * 获取 DevHelper
     * @return {@link DevHelper}
     */
    public DevHelper devHelper() {
        return this;
    }

    // ===========
    // = Handler =
    // ===========

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     * @return {@link DevHelper}
     */
    public DevHelper postRunnable(final Runnable runnable) {
        HandlerUtils.postRunnable(runnable);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @return {@link DevHelper}
     */
    public DevHelper postRunnable(final Runnable runnable, final long delayMillis) {
        HandlerUtils.postRunnable(runnable, delayMillis);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @return {@link DevHelper}
     */
    public DevHelper postRunnable(final Runnable runnable, final long delayMillis, final int number, final int interval) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable      可执行的任务
     * @param delayMillis   延迟时间
     * @param number        轮询次数
     * @param interval      轮询时间
     * @param onEndListener 结束通知
     * @return {@link DevHelper}
     */
    public DevHelper postRunnable(final Runnable runnable, final long delayMillis, final int number, final int interval, final HandlerUtils.OnEndListener onEndListener) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval, onEndListener);
        return this;
    }

    /**
     * 在主线程 Handler 中清除任务
     * @param runnable 需要清除的任务
     * @return {@link DevHelper}
     */
    public DevHelper removeRunnable(final Runnable runnable) {
        HandlerUtils.removeRunnable(runnable);
        return this;
    }

    // ================
    // = TimerManager =
    // ================

    /**
     * 运行定时器
     * @param timer {@link TimerManager.AbsTimer}
     * @return {@link DevHelper}
     */
    public DevHelper startTimer(final TimerManager.AbsTimer timer) {
        if (timer != null) timer.startTimer();
        return this;
    }

    /**
     * 关闭定时器
     * @param timer {@link TimerManager.AbsTimer}
     * @return {@link DevHelper}
     */
    public DevHelper closeTimer(final TimerManager.AbsTimer timer) {
        if (timer != null) timer.closeTimer();
        return this;
    }

    // ===============
    // = BitmapUtils =
    // ===============

    /**
     * Bitmap 通知回收
     * @param bitmap 待回收图片
     * @return {@link DevHelper}
     */
    public DevHelper recycle(final Bitmap bitmap) {
        BitmapUtils.recycle(bitmap);
        return this;
    }

    // ==============
    // = ImageUtils =
    // ==============

    /**
     * 保存图片到 SDCard - JPEG
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardJPEG(final Bitmap bitmap, final String filePath) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.JPEG, 100);
    }

    /**
     * 保存图片到 SDCard - JPEG
     * @param bitmap 待保存图片
     * @param file   保存路径
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardJPEG(final Bitmap bitmap, final File file) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.JPEG, 100);
    }

    // =

    /**
     * 保存图片到 SDCard - JPEG
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @param quality  质量
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardJPEG(final Bitmap bitmap, final String filePath,
                                            @IntRange(from = 0, to = 100) final int quality) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.JPEG, quality);
    }

    /**
     * 保存图片到 SDCard - JPEG
     * @param bitmap  待保存图片
     * @param file    保存路径
     * @param quality 质量
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardJPEG(final Bitmap bitmap, final File file,
                                            @IntRange(from = 0, to = 100) final int quality) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.JPEG, quality);
    }

    // =

    /**
     * 保存图片到 SDCard - PNG
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardPNG(final Bitmap bitmap, final String filePath) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * 保存图片到 SDCard - PNG
     * @param bitmap 待保存图片
     * @param file   保存路径
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardPNG(final Bitmap bitmap, final File file) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.PNG, 100);
    }

    // =

    /**
     * 保存图片到 SDCard - PNG
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @param quality  质量
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardPNG(final Bitmap bitmap, final String filePath,
                                           @IntRange(from = 0, to = 100) final int quality) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.PNG, quality);
    }

    /**
     * 保存图片到 SDCard - PNG
     * @param bitmap  待保存图片
     * @param file    保存路径
     * @param quality 质量
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardPNG(final Bitmap bitmap, final File file,
                                           @IntRange(from = 0, to = 100) final int quality) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.PNG, quality);
    }

    // =

    /**
     * 保存图片到 SDCard - WEBP
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardWEBP(final Bitmap bitmap, final String filePath) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.WEBP, 100);
    }

    /**
     * 保存图片到 SDCard - WEBP
     * @param bitmap 待保存图片
     * @param file   保存路径
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardWEBP(final Bitmap bitmap, final File file) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.WEBP, 100);
    }

    // =

    /**
     * 保存图片到 SDCard - WEBP
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @param quality  质量
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardWEBP(final Bitmap bitmap, final String filePath,
                                            @IntRange(from = 0, to = 100) final int quality) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.WEBP, quality);
    }

    /**
     * 保存图片到 SDCard - WEBP
     * @param bitmap  待保存图片
     * @param file    保存路径
     * @param quality 质量
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCardWEBP(final Bitmap bitmap, final File file,
                                            @IntRange(from = 0, to = 100) final int quality) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.WEBP, quality);
    }

    // =

    /**
     * 保存图片到 SDCard
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  质量
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCard(final Bitmap bitmap, final String filePath, final Bitmap.CompressFormat format,
                                        @IntRange(from = 0, to = 100) final int quality) {
        ImageUtils.saveBitmapToSDCard(bitmap, filePath, format, quality);
        return this;
    }

    /**
     * 保存图片到 SDCard
     * @param bitmap  待保存图片
     * @param file    保存路径
     * @param format  如 Bitmap.CompressFormat.PNG
     * @param quality 质量
     * @return {@link DevHelper}
     */
    public DevHelper saveBitmapToSDCard(final Bitmap bitmap, final File file, final Bitmap.CompressFormat format,
                                        @IntRange(from = 0, to = 100) final int quality) {
        ImageUtils.saveBitmapToSDCard(bitmap, file, format, quality);
        return this;
    }

    // =================
    // = EditTextUtils =
    // =================

    /**
     * 添加输入监听事件
     * @param editText {@link EditText}
     * @param watcher  输入监听
     * @return {@link DevHelper}
     */
    public DevHelper addTextChangedListener(final EditText editText, final TextWatcher watcher) {
        EditTextUtils.addTextChangedListener(editText, watcher);
        return this;
    }

    /**
     * 移除输入监听事件
     * @param editText {@link EditText}
     * @param watcher  输入监听
     * @return {@link DevHelper}
     */
    public DevHelper removeTextChangedListener(final EditText editText, final TextWatcher watcher) {
        EditTextUtils.removeTextChangedListener(editText, watcher);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText    {@link EditText}
     * @param keyListener {@link KeyListener}
     * @return {@link DevHelper}
     */
    public DevHelper setKeyListener(final EditText editText, final KeyListener keyListener) {
        EditTextUtils.setKeyListener(editText, keyListener);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容, 如: 0123456789
     * @return {@link DevHelper}
     */
    public DevHelper setKeyListener(final EditText editText, final String accepted) {
        EditTextUtils.setKeyListener(editText, accepted);
        return this;
    }

    /**
     * 设置 KeyListener
     * @param editText {@link EditText}
     * @param accepted 允许输入的内容
     * @return {@link DevHelper}
     */
    public DevHelper setKeyListener(final EditText editText, final char[] accepted) {
        EditTextUtils.setKeyListener(editText, accepted);
        return this;
    }

    // =======================
    // = AnalysisRecordUtils =
    // =======================

    /**
     * 日志记录
     * @param fileInfo {@link AnalysisRecordUtils.FileInfo}
     * @param logs     日志内容数组
     * @return {@link DevHelper}
     */
    public DevHelper record(final AnalysisRecordUtils.FileInfo fileInfo, final String... logs) {
        AnalysisRecordUtils.record(fileInfo, logs);
        return this;
    }

    // ==============
    // = CleanUtils =
    // ==============

    /**
     * 清除内部缓存 - path /data/data/package/cache
     * @return {@link DevHelper}
     */
    public DevHelper cleanInternalCache() {
        CleanUtils.cleanInternalCache();
        return this;
    }

    /**
     * 清除内部文件 - path /data/data/package/files
     * @return {@link DevHelper}
     */
    public DevHelper cleanInternalFiles() {
        CleanUtils.cleanInternalFiles();
        return this;
    }

    /**
     * 清除内部数据库 - path /data/data/package/databases
     * @return {@link DevHelper}
     */
    public DevHelper cleanInternalDbs() {
        CleanUtils.cleanInternalDbs();
        return this;
    }

    /**
     * 根据名称清除数据库 - path /data/data/package/databases/dbName
     * @param dbName 数据库名
     * @return {@link DevHelper}
     */
    public DevHelper cleanInternalDbByName(final String dbName) {
        CleanUtils.cleanInternalDbByName(dbName);
        return this;
    }

    /**
     * 清除内部 SP - path /data/data/package/shared_prefs
     * @return {@link DevHelper}
     */
    public DevHelper cleanInternalSp() {
        CleanUtils.cleanInternalSp();
        return this;
    }

    /**
     * 清除外部缓存 - path /storage/emulated/0/android/data/package/cache
     * @return {@link DevHelper}
     */
    public DevHelper cleanExternalCache() {
        CleanUtils.cleanExternalCache();
        return this;
    }

    // =

    /**
     * 清除自定义路径下的文件, 使用需小心请不要误删, 而且只支持目录下的文件删除
     * @param filePath 文件路径
     * @return {@link DevHelper}
     */
    public DevHelper cleanCustomDir(final String filePath) {
        CleanUtils.cleanCustomDir(filePath);
        return this;
    }

    /**
     * 清除自定义路径下的文件, 使用需小心请不要误删, 而且只支持目录下的文件删除
     * @param file 文件路径
     * @return {@link DevHelper}
     */
    public DevHelper cleanCustomDir(final File file) {
        CleanUtils.cleanCustomDir(file);
        return this;
    }

    /**
     * 清除本应用所有的数据
     * @param filePaths 文件路径数组
     * @return {@link DevHelper}
     */
    public DevHelper cleanApplicationData(final String... filePaths) {
        CleanUtils.cleanApplicationData(filePaths);
        return this;
    }

    // ==================
    // = ClipboardUtils =
    // ==================

    /**
     * 复制文本到剪贴板
     * @param text 文本
     * @return {@link DevHelper}
     */
    public DevHelper copyText(final CharSequence text) {
        ClipboardUtils.copyText(text);
        return this;
    }

    /**
     * 复制 URI 到剪贴板
     * @param uri {@link Uri}
     * @return {@link DevHelper}
     */
    public DevHelper copyUri(final Uri uri) {
        ClipboardUtils.copyUri(uri);
        return this;
    }

    /**
     * 复制意图到剪贴板
     * @param intent 意图
     * @return {@link DevHelper}
     */
    public DevHelper copyIntent(final Intent intent) {
        ClipboardUtils.copyIntent(intent);
        return this;
    }

    // ========================
    // = ContentResolverUtils =
    // ========================

    /**
     * 通知刷新本地资源
     * @param file 文件
     * @return {@link DevHelper}
     */
    public DevHelper notifyMediaStore(final File file) {
        ContentResolverUtils.notifyMediaStore(file);
        return this;
    }

    /**
     * 添加图片到系统相册 ( 包含原图、相册图, 会存在两张 ) - 想要一张, 直接调用 notifyMediaStore()
     * @param file     文件
     * @param fileName 文件名
     * @param isNotify 是否广播通知图库扫描
     * @return {@link DevHelper}
     */
    public DevHelper insertImageIntoMediaStore(final File file, final String fileName, final boolean isNotify) {
        ContentResolverUtils.insertImageIntoMediaStore(file, fileName, isNotify);
        return this;
    }

    // =

    /**
     * 添加视频到系统相册
     * @param file 文件
     * @return {@link DevHelper}
     */
    public DevHelper insertVideoIntoMediaStore(final File file) {
        ContentResolverUtils.insertVideoIntoMediaStore(file);
        return this;
    }

    /**
     * 保存到系统相册
     * @param file       文件
     * @param createTime 创建时间
     * @param isVideo    是否视频
     * @param mimeType   资源类型
     * @return {@link DevHelper}
     */
    public DevHelper insertIntoMediaStore(final File file, final long createTime, final boolean isVideo, final String mimeType) {
        ContentResolverUtils.insertIntoMediaStore(file, createTime, isVideo, mimeType);
        return this;
    }

    // ===============
    // = DialogUtils =
    // ===============

    /**
     * 显示 Dialog
     * @param dialog {@link Dialog}
     * @param <T>    泛型
     * @return {@link DevHelper}
     */
    public <T extends Dialog> DevHelper showDialog(final T dialog) {
        DialogUtils.showDialog(dialog);
        return this;
    }

    /**
     * 关闭 Dialog
     * @param dialog {@link Dialog}
     * @return {@link DevHelper}
     */
    public DevHelper closeDialog(final Dialog dialog) {
        DialogUtils.closeDialog(dialog);
        return this;
    }

    /**
     * 关闭多个 Dialog
     * @param dialogs {@link Dialog} 数组
     * @return {@link DevHelper}
     */
    public DevHelper closeDialogs(final Dialog... dialogs) {
        DialogUtils.closeDialogs(dialogs);
        return this;
    }

    // =

    /**
     * 关闭 DialogFragment
     * @param dialog {@link DialogFragment}
     * @return {@link DevHelper}
     */
    public DevHelper closeDialog(final DialogFragment dialog) {
        DialogUtils.closeDialog(dialog);
        return this;
    }

    /**
     * 关闭多个 DialogFragment
     * @param dialogs {@link DialogFragment} 数组
     * @return {@link DevHelper}
     */
    public DevHelper closeDialogs(final DialogFragment... dialogs) {
        DialogUtils.closeDialogs(dialogs);
        return this;
    }

    // =

    /**
     * 关闭 PopupWindow
     * @param popupWindow {@link PopupWindow}
     * @return {@link DevHelper}
     */
    public DevHelper closePopupWindow(final PopupWindow popupWindow) {
        DialogUtils.closePopupWindow(popupWindow);
        return this;
    }

    /**
     * 关闭多个 PopupWindow
     * @param popupWindows {@link PopupWindow} 数组
     * @return {@link DevHelper}
     */
    public DevHelper closePopupWindows(final PopupWindow... popupWindows) {
        DialogUtils.closePopupWindows(popupWindows);
        return this;
    }

    // =

    /**
     * 自动关闭 dialog
     * @param dialog      {@link Dialog}
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param <T>         泛型
     * @return {@link DevHelper}
     */
    public <T extends Dialog> DevHelper autoCloseDialog(final T dialog, final long delayMillis, final Handler handler) {
        DialogUtils.autoCloseDialog(dialog, delayMillis, handler);
        return this;
    }

    /**
     * 自动关闭 DialogFragment
     * @param dialog      {@link DialogFragment}
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param <T>         泛型
     * @return {@link DevHelper}
     */
    public <T extends DialogFragment> DevHelper autoCloseDialog(final T dialog, final long delayMillis, final Handler handler) {
        DialogUtils.autoCloseDialog(dialog, delayMillis, handler);
        return this;
    }

    /**
     * 自动关闭 PopupWindow
     * @param popupWindow {@link PopupWindow}
     * @param delayMillis 延迟关闭时间
     * @param handler     {@link Handler}
     * @param <T>         泛型
     * @return {@link DevHelper}
     */
    public <T extends PopupWindow> DevHelper autoClosePopupWindow(final T popupWindow, final long delayMillis, final Handler handler) {
        DialogUtils.autoClosePopupWindow(popupWindow, delayMillis, handler);
        return this;
    }

    // =================
    // = KeyBoardUtils =
    // =================

    // ==============
    // = 打开软键盘 =
    // ==============

    /**
     * 打开软键盘
     * @param editText {@link EditText}
     * @return {@link DevHelper}
     */
    public DevHelper openKeyboard(final EditText editText) {
        KeyBoardUtils.openKeyboard(editText);
        return this;
    }

    /**
     * 打开软键盘
     * @param editText {@link EditText}
     * @param handler  {@link Handler}
     * @return {@link DevHelper}
     */
    public DevHelper openKeyboard(final EditText editText, final Handler handler) {
        KeyBoardUtils.openKeyboard(editText, handler);
        return this;
    }

    /**
     * 打开软键盘
     * @param editText    {@link EditText}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link DevHelper}
     */
    public DevHelper openKeyboard(final EditText editText, final Handler handler, final int delayMillis) {
        KeyBoardUtils.openKeyboard(editText, handler, delayMillis);
        return this;
    }

    // =

    /**
     * 打开软键盘
     * @return {@link DevHelper}
     */
    public DevHelper openKeyboard() {
        KeyBoardUtils.openKeyboard();
        return this;
    }

    /**
     * 打开软键盘
     * @param handler {@link Handler}
     * @return {@link DevHelper}
     */
    public DevHelper openKeyboard(final Handler handler) {
        KeyBoardUtils.openKeyboard(handler);
        return this;
    }

    /**
     * 打开软键盘
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link DevHelper}
     */
    public DevHelper openKeyboard(final Handler handler, final int delayMillis) {
        KeyBoardUtils.openKeyboard(handler, delayMillis);
        return this;
    }

    // ==============
    // = 关闭软键盘 =
    // ==============

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final EditText editText) {
        KeyBoardUtils.closeKeyboard(editText);
        return this;
    }

    /**
     * 关闭软键盘
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard() {
        KeyBoardUtils.closeKeyboard();
        return this;
    }

    /**
     * 关闭软键盘
     * @param activity {@link Activity}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final Activity activity) {
        KeyBoardUtils.closeKeyboard(activity);
        return this;
    }

    /**
     * 关闭 dialog 中打开的键盘
     * @param dialog {@link Dialog}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final Dialog dialog) {
        KeyBoardUtils.closeKeyboard(dialog);
        return this;
    }

    // =

    /**
     * 关闭软键盘 - 特殊处理
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyBoardSpecial(final EditText editText, final Dialog dialog) {
        KeyBoardUtils.closeKeyBoardSpecial(editText, dialog);
        return this;
    }

    /**
     * 关闭软键盘 - 特殊处理
     * @param editText {@link EditText}
     * @param dialog   {@link Dialog}
     * @param handler  {@link Handler}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyBoardSpecial(final EditText editText, final Dialog dialog, final Handler handler) {
        KeyBoardUtils.closeKeyBoardSpecial(editText, dialog, handler);
        return this;
    }

    /**
     * 关闭软键盘 - 特殊处理 ( 两个都关闭 )
     * @param editText    {@link EditText}
     * @param dialog      {@link Dialog}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyBoardSpecial(final EditText editText, final Dialog dialog, final Handler handler, final int delayMillis) {
        KeyBoardUtils.closeKeyBoardSpecial(editText, dialog, handler, delayMillis);
        return this;
    }

    // =

    /**
     * 关闭软键盘
     * @param editText {@link EditText}
     * @param handler  {@link Handler}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final EditText editText, final Handler handler) {
        KeyBoardUtils.closeKeyboard(editText, handler);
        return this;
    }

    /**
     * 关闭软键盘
     * @param editText    {@link EditText}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final EditText editText, final Handler handler, final int delayMillis) {
        KeyBoardUtils.closeKeyboard(editText, handler, delayMillis);
        return this;
    }

    /**
     * 关闭软键盘
     * @param handler {@link Handler}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final Handler handler) {
        KeyBoardUtils.closeKeyboard(handler);
        return this;
    }

    /**
     * 关闭软键盘
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final Handler handler, final int delayMillis) {
        KeyBoardUtils.closeKeyboard(handler, delayMillis);
        return this;
    }

    /**
     * 关闭软键盘
     * @param activity {@link Activity}
     * @param handler  {@link Handler}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final Activity activity, final Handler handler) {
        KeyBoardUtils.closeKeyboard(activity, handler);
        return this;
    }

    /**
     * 关闭软键盘
     * @param activity    {@link Activity}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final Activity activity, final Handler handler, final int delayMillis) {
        KeyBoardUtils.closeKeyboard(activity, handler, delayMillis);
        return this;
    }

    /**
     * 关闭软键盘
     * @param dialog  {@link Dialog}
     * @param handler {@link Handler}
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final Dialog dialog, final Handler handler) {
        KeyBoardUtils.closeKeyboard(dialog, handler);
        return this;
    }

    /**
     * 关闭软键盘
     * @param dialog      {@link Dialog}
     * @param handler     {@link Handler}
     * @param delayMillis 延迟时间 ( 毫秒 )
     * @return {@link DevHelper}
     */
    public DevHelper closeKeyboard(final Dialog dialog, final Handler handler, final int delayMillis) {
        KeyBoardUtils.closeKeyboard(dialog, handler, delayMillis);
        return this;
    }

    // ================================
    // = 点击非 EditText 则隐藏输入法 =
    // ================================

    /**
     * 设置某个 View 内所有非 EditText 的子 View OnTouchListener 事件
     * @param view     {@link View}
     * @param activity {@link Activity}
     * @return {@link DevHelper}
     */
    public DevHelper judgeView(final View view, final Activity activity) {
        KeyBoardUtils.judgeView(view, activity);
        return this;
    }

    /**
     * 注册软键盘改变监听
     * @param activity {@link Activity}
     * @param listener {@link KeyBoardUtils.OnSoftInputChangedListener}
     * @return {@link DevHelper}
     */
    public DevHelper registerSoftInputChangedListener(final Activity activity, final KeyBoardUtils.OnSoftInputChangedListener listener) {
        KeyBoardUtils.registerSoftInputChangedListener(activity, listener);
        return this;
    }

    /**
     * 注册软键盘改变监听
     * @param activity {@link Activity}
     * @param listener {@link KeyBoardUtils.OnSoftInputChangedListener}
     * @return {@link DevHelper}
     */
    public DevHelper registerSoftInputChangedListener2(final Activity activity, final KeyBoardUtils.OnSoftInputChangedListener listener) {
        KeyBoardUtils.registerSoftInputChangedListener2(activity, listener);
        return this;
    }

    // =================
    // = LanguageUtils =
    // =================

    /**
     * 修改系统语言 (APP 多语言, 单独改变 APP 语言 )
     * @param context {@link Context}
     * @param locale  {@link Locale}
     * @return {@link DevHelper}
     */
    public DevHelper applyLanguage(final Context context, final Locale locale) {
        LanguageUtils.applyLanguage(context, locale);
        return this;
    }

    /**
     * 修改系统语言 (APP 多语言, 单独改变 APP 语言 )
     * @param context  {@link Context}
     * @param language 语言
     * @return {@link DevHelper}
     */
    public DevHelper applyLanguage(final Context context, final String language) {
        LanguageUtils.applyLanguage(context, language);
        return this;
    }

    // =================
    // = ListenerUtils =
    // =================

    /**
     * 设置点击事件
     * @param onClickListener {@link View.OnClickListener}
     * @param views           View 数组
     * @return {@link DevHelper}
     */
    public DevHelper setOnClicks(final View.OnClickListener onClickListener, final View... views) {
        ListenerUtils.setOnClicks(onClickListener, views);
        return this;
    }

    /**
     * 设置长按事件
     * @param onLongClickListener {@link View.OnLongClickListener}
     * @param views               View 数组
     * @return {@link DevHelper}
     */
    public DevHelper setOnLongClicks(final View.OnLongClickListener onLongClickListener, final View... views) {
        ListenerUtils.setOnLongClicks(onLongClickListener, views);
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param view  待添加点击范围 View
     * @param range 点击范围
     * @return {@link DevHelper}
     */
    public DevHelper addTouchArea(final View view, final int range) {
        ClickUtils.addTouchArea(view, range);
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param view   待添加点击范围 View
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @return {@link DevHelper}
     */
    public DevHelper addTouchArea(final View view, final int top, final int bottom, final int left, final int right) {
        ClickUtils.addTouchArea(view, top, bottom, left, right);
        return this;
    }

    // =====================
    // = NotificationUtils =
    // =====================

    /**
     * 移除通知 - 移除所有通知 ( 只是针对当前 Context 下的 Notification)
     * @return {@link DevHelper}
     */
    public DevHelper cancelAllNotification() {
        NotificationUtils.cancelAll();
        return this;
    }

    /**
     * 移除通知 - 移除标记为 id 的通知 ( 只是针对当前 Context 下的所有 Notification)
     * @param args 消息 id 集合
     * @return {@link DevHelper}
     */
    public DevHelper cancelNotification(final int... args) {
        NotificationUtils.cancel(args);
        return this;
    }

    /**
     * 移除通知 - 移除标记为 id 的通知 ( 只是针对当前 Context 下的所有 Notification)
     * @param tag 标记 TAG
     * @param id  消息 id
     * @return {@link DevHelper}
     */
    public DevHelper cancelNotification(final String tag, final int id) {
        NotificationUtils.cancel(tag, id);
        return this;
    }

    /**
     * 进行通知
     * @param id           消息 id
     * @param notification {@link Notification}
     * @return {@link DevHelper}
     */
    public DevHelper notifyNotification(final int id, final Notification notification) {
        NotificationUtils.notify(id, notification);
        return this;
    }

    /**
     * 进行通知
     * @param tag          标记 TAG
     * @param id           消息 id
     * @param notification {@link Notification}
     * @return {@link DevHelper}
     */
    public DevHelper notifyNotification(final String tag, final int id, final Notification notification) {
        NotificationUtils.notify(tag, id, notification);
        return this;
    }

    // =================
    // = ResourceUtils =
    // =================

    /**
     * 获取 Assets 资源文件数据并保存到本地
     * @param fileName 文件名
     * @param file     文件保存地址
     * @return {@link DevHelper}
     */
    public DevHelper saveAssetsFormFile(final String fileName, final File file) {
        ResourceUtils.saveAssetsFormFile(fileName, file);
        return this;
    }

    /**
     * 获取 Raw 资源文件数据并保存到本地
     * @param resId 资源 id
     * @param file  文件保存地址
     * @return {@link DevHelper}
     */
    public DevHelper saveRawFormFile(@RawRes final int resId, final File file) {
        ResourceUtils.saveRawFormFile(resId, file);
        return this;
    }

    // ===============
    // = ScreenUtils =
    // ===============

    /**
     * 设置禁止截屏
     * @param activity {@link Activity}
     * @return {@link DevHelper}
     */
    public DevHelper setWindowSecure(final Activity activity) {
        ScreenUtils.setWindowSecure(activity);
        return this;
    }

    /**
     * 设置屏幕为全屏
     * @param activity {@link Activity}
     * @return {@link DevHelper}
     */
    public DevHelper setFullScreen(final Activity activity) {
        ScreenUtils.setFullScreen(activity);
        return this;
    }

    /**
     * 设置屏幕为横屏
     * @param activity {@link Activity}
     * @return {@link DevHelper}
     */
    public DevHelper setLandscape(final Activity activity) {
        ScreenUtils.setLandscape(activity);
        return this;
    }

    /**
     * 设置屏幕为竖屏
     * @param activity {@link Activity}
     * @return {@link DevHelper}
     */
    public DevHelper setPortrait(final Activity activity) {
        ScreenUtils.setPortrait(activity);
        return this;
    }

    /**
     * 切换屏幕方向
     * @param activity {@link Activity}
     * @return {@link DevHelper}
     */
    public DevHelper toggleScreenOrientation(final Activity activity) {
        ScreenUtils.toggleScreenOrientation(activity);
        return this;
    }

    // =============
    // = SizeUtils =
    // =============

    /**
     * 在 onCreate 中获取视图的尺寸 - 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高
     * @param view     {@link View}
     * @param listener {@link SizeUtils.onGetSizeListener}
     * @return {@link DevHelper}
     */
    public DevHelper forceGetViewSize(final View view, final SizeUtils.onGetSizeListener listener) {
        SizeUtils.forceGetViewSize(view, listener);
        return this;
    }

    // ==================
    // = VibrationUtils =
    // ==================

    /**
     * 震动
     * @param milliseconds 震动时长 ( 毫秒 )
     * @return {@link DevHelper}
     */
    @SuppressLint("MissingPermission")
    public DevHelper vibrate(final long milliseconds) {
        VibrationUtils.vibrate(milliseconds);
        return this;
    }

    /**
     * pattern 模式震动
     * @param pattern new long[]{400, 800, 1200, 1600}, 就是指定在 400ms、800ms、1200ms、1600ms 这些时间点交替启动、关闭手机震动器
     * @param repeat  指定 pattern 数组的索引, 指定 pattern 数组中从 repeat 索引开始的震动进行循环,
     *                -1 表示只震动一次, 非 -1 表示从 pattern 数组指定下标开始重复震动
     * @return {@link DevHelper}
     */
    @SuppressLint("MissingPermission")
    public DevHelper vibrate(final long[] pattern, final int repeat) {
        VibrationUtils.vibrate(pattern, repeat);
        return this;
    }

    /**
     * 取消震动
     * @return {@link DevHelper}
     */
    @SuppressLint("MissingPermission")
    public DevHelper cancel() {
        VibrationUtils.cancel();
        return this;
    }

    // ==============
    // = CloseUtils =
    // ==============

    /**
     * 关闭 IO
     * @param closeables Closeable[]
     * @return {@link DevHelper}
     */
    public DevHelper closeIO(final Closeable... closeables) {
        CloseUtils.closeIO(closeables);
        return this;
    }

    /**
     * 安静关闭 IO
     * @param closeables Closeable[]
     * @return {@link DevHelper}
     */
    public DevHelper closeIOQuietly(final Closeable... closeables) {
        CloseUtils.closeIOQuietly(closeables);
        return this;
    }

    // ==========================
    // = HttpURLConnectionUtils =
    // ==========================

    /**
     * 获取网络时间 - 默认使用百度链接
     * @param timeCallBack 请求时间回调接口
     * @return {@link DevHelper}
     */
    public DevHelper getNetTime(final HttpURLConnectionUtils.TimeCallBack timeCallBack) {
        HttpURLConnectionUtils.getNetTime(timeCallBack);
        return this;
    }

    /**
     * 获取网络时间
     * @param urlStr       请求地址
     * @param timeCallBack 请求时间回调接口
     * @return {@link DevHelper}
     */
    public DevHelper getNetTime(final String urlStr, final HttpURLConnectionUtils.TimeCallBack timeCallBack) {
        HttpURLConnectionUtils.getNetTime(urlStr, timeCallBack);
        return this;
    }

    // ==============
    // = TimeKeeper =
    // ==============

    /**
     * 设置等待一段时间后, 通知方法 ( 异步 )
     * @param keepTimeMillis 堵塞时间 ( 毫秒 )
     * @param endCallback    结束回调通知
     * @return {@link DevHelper}
     */
    public DevHelper waitForEndAsyn(final long keepTimeMillis, final TimeKeeper.OnEndCallback endCallback) {
        mTimeKeeper.waitForEndAsyn(keepTimeMillis, endCallback);
        return this;
    }

    /**
     * 设置等待一段时间后, 通知方法 ( 同步 )
     * @param keepTimeMillis 堵塞时间 ( 毫秒 )
     * @param endCallback    结束回调通知
     * @return {@link DevHelper}
     */
    public DevHelper waitForEnd(final long keepTimeMillis, final TimeKeeper.OnEndCallback endCallback) {
        mTimeKeeper.waitForEnd(keepTimeMillis, endCallback);
        return this;
    }
}