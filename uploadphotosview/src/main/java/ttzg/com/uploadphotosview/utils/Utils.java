package ttzg.com.uploadphotosview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 操作相关工具类
 */
public final class Utils {
    public static String formatLongToTimeStr(Long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(ms);
    }

    public static String setPhoneNoByUri(Context mContext, Uri contactData) {

        try {

            // 获取联系人cursor
            Cursor cursor = mContext.getContentResolver().query(contactData, null, null,
                    null, null);
            cursor.moveToFirst();

            // 获取联系人ID
            String contactsId = cursor.getString(cursor
                    .getColumnIndexOrThrow(ContactsContract.Contacts._ID));

            // 获取联系人判断是否有电话
            String hasPhone = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1")) {
                hasPhone = "true";
            } else {
                hasPhone = "false";
            }

            if (Boolean.parseBoolean(hasPhone)) {

                // 多个电话，取一个
                Cursor phones = mContext.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + contactsId, null, null);

                phones.moveToFirst();

                String number = phones.getString(phones
                        .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                return number;
            }
        } catch (Exception e) {
            ToastUtil.show(mContext, "此联系人不可选取，请手动输入");

        }
        return "";
    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static boolean isUnion20Code(String couponResult) {
        if (couponResult == null) {
            return false;
        }
        String reg = "https://qr.95516.com/";
        return couponResult.indexOf(reg) == 0;
    }


    public static boolean isUnionCard(String couponResult) {
        String cardNo = couponResult.trim();
        if (cardNo.length() != 19) {
            return false;
        }

        if (!cardNo.substring(0, 2).equals("62")) {
            return false;
        }
        return true;
    }

    /**
     * 打开第三方地图
     *
     * @param context
     * @param latitude
     * @param longitude
     * @param addr
     */
    public static void navigation(Context context, String latitude, String longitude,
                                  String addr) throws Exception {
        Uri mUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + addr + ")");
        Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);

    }

    /**
     * 分享
     *
     * @param mContext
     * @param activityTitle
     * @param msgText
     */
    public static void shareMsg(Activity mContext, String activityTitle, String msgText) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(Intent.createChooser(intent, activityTitle));
    }


    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    public static int getAndroiodScreenProperty(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        int height = dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);//屏幕宽度(dp)
        return width;
    }

    /**
     * @param editText
     * @return
     */
    public static boolean isNull(EditText editText) {
        if (!isEmpty(editText.getText().toString().trim())) {
            return true;
        }
        return false;
    }


    /**
     * @param termTxnTime
     * @return String
     */
    public static String getSectionDesc(String termTxnTime) {
        Date termTxnDate = null;
        try {
            termTxnDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(termTxnTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String orderTimeStr = DateFormat.getDateInstance(DateFormat.FULL).format(termTxnDate);
        return orderTimeStr;
    }

    public static String getVersionName(Context context) {
        String versionName = "1.0";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    static DecimalFormat df = null;

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNull(Object obj) {
        if (null == obj || obj == "" || obj.equals("") || obj.toString().contentEquals("null")) {
            return true;
        }
        return false;
    }

    public static Bitmap stringtoBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static boolean isNumber(String str) {
        String reg = "\\d+(\\.\\d+)?";
        // java.util.regex.Pattern
        // pattern=java.util.regex.Pattern.compile("[0-9]*");
        Pattern pattern = Pattern.compile(reg);

        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param httpUrl
     * @return
     */
    public static String getHostTitle(String httpUrl) {
        String title = null;
        try {
            URL url = new URL(httpUrl);
            title = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return title;
    }


    public static boolean isNotBlank(String str) {
        return ((str != null) && (str.trim().length() > 0));
    }

    public static boolean isBlank(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    public static boolean isInviteCode(String str) {
        if (str != null) {
            str = str.trim();
            Pattern p = Pattern.compile("[0-9a-zA-Z\u4e00-\u9fa5]+",
                    Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            return m.matches();
        }
        return false;

    }

    public static int ceil(float f) {
        int i = 0;
        if (f > 0 && f <= 1) {
            i = 1;
        } else if (f > 1 && f <= 2) {
            i = 2;
        } else if (f > 2 && f <= 3) {
            i = 3;
        } else if (f > 3 && f <= 4) {
            i = 4;
        } else if (f > 4 && f <= 5) {
            i = 5;
        }
        return i;
    }

    /**
     * 判断字符串是否为邮箱
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher mc = pattern.matcher(str);
        return mc.matches();
    }

    /**
     * 判断字符串是否都是字母
     *
     * @param str
     * @return
     */
    public static boolean isStrAndLetter(String str) {
        boolean flag = true;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fbf]+")
                    || str.substring(i, i + 1).matches("[a-zA-Z]")) {
            } else {
                return false;
            }
        }
        return flag;
    }

    /**
     * <字符串截取方法　截取掉字符串最后一个字符>
     *
     * @param intercept
     * @return
     * @author christineRuan
     * @date 2013-12-17 下午5:24:24
     * @returnType String
     */
    public static String stringIntercept(String intercept) {
        return intercept.substring(0, intercept.length() - 1);
    }

    /**
     * <格式化double数据>
     *
     * @param value
     * @param doubleoFrmat
     * @return
     * @author christineRuan
     * @date 2014-1-20 下午8:06:10
     * @returnType String
     */
    public static String doubleFormat(double value, String doubleoFrmat) {
        if (df == null) {
            df = new DecimalFormat(doubleoFrmat);
        }
        return df.format(value);
    }

    /**
     * Float转String,获取2小数位字符串
     *
     * @return
     */
    public static String FloatToString(float value) {

        DecimalFormat fnum = new DecimalFormat("###,###,##0.00");
        String dd = fnum.format(value);

        return dd;

    }


    public static String formatNumber(String number, int fractionDigits) {
        double num = 0;
        try {
            num = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return formatNumber(num, fractionDigits);
    }

    public static String formatNumber(double number, int fractionDigits) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(fractionDigits);
        format.setMinimumFractionDigits(fractionDigits);
        return format.format(number);
    }

    public static String fomartAmtStr(String amt) {
        try {
            return new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            return amt;
        }

    }


    /**
     * @param s
     * @return 139 2957 7555
     */
    public static String formatPhoto(String s) {
        StringBuffer sbf = new StringBuffer();
        try {
            int length = s.toString().length();
            sbf.append(s.substring(0, 3) + "");
            sbf.append(s.substring(3, 3) + " ");
            sbf.append(s.substring(3, 7) + " ");
            sbf.append(s.substring(7, length));

        } catch (Exception e) {
            e.printStackTrace();
            String regex = "(.{4})";
            sbf.append(s.replaceAll(regex, "$1 "));
        }
        return sbf.toString();
    }

    public static String toKM(String distance) {
        if (distance != null && !"".equals(distance)) {
            if (Double.valueOf(distance).doubleValue() >= 1000) {
                NumberFormat nf = new DecimalFormat("0.0");
                return Double.parseDouble(nf.format(Double.valueOf(distance)
                        .doubleValue() / 1000)) + "公里";
            } else {
                return (int) Math.ceil(Double.valueOf(distance).doubleValue())
                        + "米";
            }
        } else {
            return "未设置";
        }
    }

    /**
     * TODO 判断是否为URL
     *
     * @FileName AppUtil.java
     * @author Simon.xin
     */
    public static boolean isUrl(String url) {
        // ^((https|http|ftp|rtsp|mms)?://)
        // String regex =
        // "^(https|http|ftp|rtsp|mms|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        // String regex =
        // "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        // Pattern patt = Pattern.compile(regex);
        // Matcher matcher = patt.matcher(url);
        // boolean isMatch = matcher.matches();
        // if (!isMatch) {
        // return false;
        // } else {
        // return true;
        // }
        if (url.indexOf("http") > -1) {
            return true;
        }
        if (url.indexOf("www") > -1) {
            return true;
        }
        if (url.indexOf("com") > -1) {
            return true;
        }
        if (url.indexOf("cn") > -1) {
            return true;
        }
        return false;
    }

    public static ArrayList<String> getParamFromUrl(String url) {
        ArrayList<String> list = null;
        if (isNull(url)) {
            return list;
        }
        try {
            String[] item = url.split("\\?");
            if (isNull(item)) {
                return list;
            }
            String[] params = item[1].split("&");
            if (isNull(params)) {
                return list;
            }
            list = new ArrayList<String>();
            for (int i = 0; i < params.length; i++) {
                list.add(params[i].split("=")[1]);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        String resultStr = uniqueId.replaceAll("-", "");
        return resultStr;
    }

    public interface OnPwdConfirmListener {
        void onPwdConfirm(String content);
    }


    public static void hiddenSoftInput(EditText et) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            et.setShowSoftInputOnFocus(false);
        } else {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(et, false);
                //edit为EditText对象
            } catch (Exception e) {
            }
        }
    }

    public static void showSoftInput(EditText editText) {
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }
}
