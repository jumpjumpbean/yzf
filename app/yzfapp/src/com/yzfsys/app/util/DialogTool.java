package com.yzfsys.app.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DialogTool {
    /**
     * 创建普通对话框
     * 
     * @param ctx 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 必填
     * @param title 标题 必填
     * @param message 显示内容 必填
     * @param btnName 按钮名称 必填
     * @param listener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
 public static Dialog createNormalDialog(Context ctx, 
   int iconId, 
   String title, 
   String message, 
   String btnPositiveName,
   OnClickListener listener1,
   String btnNegativeName,
   OnClickListener listener2) {
  Dialog dialog=null;
  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
  // 设置对话框的图标
  builder.setIcon(iconId);
  // 设置对话框的标题
  builder.setTitle(title);
  // 设置对话框的显示内容
  builder.setMessage(message);
  // 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
  builder.setPositiveButton(btnPositiveName, listener1);
  builder.setNegativeButton(btnNegativeName, listener2);
  // 创建一个普通对话框
  dialog = builder.create();
  return dialog;
 }
  
  
    /**
     * 创建列表对话框
     * 
     * @param ctx 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 必填
     * @param title 标题 必填
     * @param itemsId 字符串数组资源id 必填
     * @param listener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
 public static Dialog createListDialog(Context ctx, 
   int iconId, 
   String title, 
   String[] items, 
   OnClickListener listener,
   String btnNegativeName,
   OnClickListener listener2) {
  Dialog dialog=null;
  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
  // 设置对话框的图标
  builder.setIcon(iconId);
  // 设置对话框的标题
  builder.setTitle(title);
  // 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
  builder.setItems(items, listener);
  builder.setNegativeButton(btnNegativeName, listener2);
  // 创建一个列表对话框
  dialog = builder.create();
  return dialog;
 }
  
    /**
     * 创建单选按钮对话框
     * 
     * @param ctx 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 必填
     * @param title 标题 必填
     * @param itemsId 字符串数组资源id 必填
     * @param listener 单选按钮项监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param btnName 按钮名称 必填
     * @param listener2 按钮监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
 public static Dialog createRadioDialog(Context ctx, 
   int iconId, 
   String title, 
   String[] items, 
   OnClickListener listener,
   String btnPositiveName,
   OnClickListener listener2,
   String btnNegativeName,
   OnClickListener listener3,
   int defaultItem) {
  Dialog dialog=null;
  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
  // 设置对话框的图标
  builder.setIcon(iconId);
  // 设置对话框的标题
  builder.setTitle(title);
  // 默认上次选择单选按钮被选中
  builder.setSingleChoiceItems(items, defaultItem, listener);
  // 添加按钮
  builder.setPositiveButton(btnPositiveName, listener2);
  builder.setNegativeButton(btnNegativeName, listener3);
  // 创建一个单选按钮对话框
  dialog = builder.create();
  return dialog;
 }
  
  
    /**
     * 创建复选对话框
     * 
     * @param ctx 上下文 必填
     * @param iconId 图标，如：R.drawable.icon 必填
     * @param title 标题 必填
     * @param itemsId 字符串数组资源id 必填
     * @param flags 初始复选情况 必填
     * @param listener 单选按钮项监听器，需实现android.content.DialogInterface.OnMultiChoiceClickListener接口 必填
     * @param btnName 按钮名称 必填
     * @param listener2 按钮监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
 public static Dialog createCheckBoxDialog(Context ctx, 
   int iconId, 
   String title, 
   int itemsId, 
   boolean[] flags,
   android.content.DialogInterface.OnMultiChoiceClickListener listener,
   String btnName,
   OnClickListener listener2) {
  Dialog dialog=null;
  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
  // 设置对话框的图标
  builder.setIcon(iconId);
  // 设置对话框的标题
  builder.setTitle(title);
  builder.setMultiChoiceItems(itemsId, flags, listener);
  // 添加一个按钮
  builder.setPositiveButton(btnName, listener2) ;
  // 创建一个复选对话框
  dialog = builder.create();
  return dialog;
 }
}
