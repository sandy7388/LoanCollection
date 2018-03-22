package com.example.sandy.loancollection.printClasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.Toast;

import com.example.sandy.loancollection.activity.MainActivity;
import com.example.sandy.loancollection.selector.FileSelector;
import com.example.sandy.loancollection.selector.OnHandleFileListener;
import com.example.sandy.loancollection.session.SessionManager;
import com.ngx.mp100sdk.Enums.Alignments;
import com.ngx.mp100sdk.Enums.NGXBarcodeCommands;
import com.ngx.mp100sdk.Intefaces.INGXCallback;
import com.ngx.mp100sdk.NGXPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;



//import com.ngx.nix.selector.FileSelector;
//import com.ngx.nix.selector.OnHandleFileListener;
//import anandivivah.wayzontech.mandirdengi.R;

/**
 * A placeholder fragment containing a simple view.
 */

public class MP100MainFragment {
 //   public ArrayList<Number_selections> number_selections =new ArrayList<Number_selections>();
    public NGXPrinter ngxPrinter = MainActivity.ngxPrinter;
    public static int totatGrand=0;
    public String strDate;
    public SessionManager sessionManager;
   // public static NGXPrinter ngxPrinter ;
    public INGXCallback ingxCallback;
    public static final int RESULT_LOAD_IMAGE = 99;
    public static String alignment = "LEFT";
    //int index = 0;
    // byte[] processedData;
    // List<Byte> dummyData = new ArrayList<>();
    //boolean keepProcessing = true;
    public final String fileName = "image/ngx.png";
    final String[] mFileFilter = {".png", ".bmp", ".jpeg", ".jpg"};
  //  public static ReprintMainPOJO reprintMainPOJO;

    public Context context;

//    public View.OnClickListener onBtnAlignmentClicked = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.btnLeftAlign:
//                    alignment = "LEFT";
//                    break;
//                case R.id.btnCenterAlign:
//                    alignment = "CENTER";
//                    break;
//                case R.id.btnRightAlign:
//                    alignment = "RIGHT";
//                    break;
//            }
//        }
//    };
//  public NGXPrinter ngxPrinter = MP100Main.ngxPrinter;
//  public BluetoothConnectivity mBtp = MP100Main.mBtp;

    public boolean isBoldStyleSet = false;
    public int selectedFontSize = 24;
    public FileSelector mFileSel;

    /**
     * Custom File handler for showing image file selection popup
     */
    
    OnHandleFileListener mLoadFileListener = new OnHandleFileListener() {
        @Override
        public void handleFile(final String filePath) {
            Uri fileUri = Uri.parse(filePath);
            try {
                ngxPrinter.printLogo(fileUri.getPath());
            } catch (Exception e) {
          //      Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            mFileSel.dismiss();
        }
    };
   //public EditText mEdit;

    public MP100MainFragment(Context context) {
        this.context=context;
        ngxPrinter = MainActivity.ngxPrinter;
        sessionManager=new SessionManager(context);

       // ngxPrinter=new NGXPrinter();
//        try{
//            ngxPrinter= NGXPrinter.getNgxPrinterInstance();
//            ingxCallback = new INGXCallback() {
//                @Override
//                public void onRunResult(boolean isSuccess) {
//                    Log.i("NGX", "onRunResult:" + isSuccess);
//                }
//
//                @Override
//                public void onReturnString(String result) {
//                    Log.i("NGX", "onReturnString:" + result);
//                }
//
//                @Override
//                public void onRaiseException(int code, String msg) {
//                    Log.i("NGX", "onRaiseException:" + code + ":" + msg);
//                }
//            };
//            ngxPrinter.initService(context,ingxCallback);
//
//        }
//        catch (Exception e)
//        {
//
//        }


    }

//    public  void setPrintFromAnotherActivity(String reciptId)
//    {
//        reprintMainPOJO=new ReprintMainPOJO();
//        totatGrand=0;
//        String s1=reciptId;
//        setBoldStyleSet();
//        setPrintLineLeft("PLAY BOOK LUCKY COUPON");
//        setStyleNormal();
//        setPrintLineLeft(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
//        reprintMainPOJO.setAgents_name(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
//        setPrintLineLeft("Code No: "+s1);
//        reprintMainPOJO.setBarcode(s1);
//        setPrintLineLeft("Date: "+ ConfigCommonClass.getCurrentDateAndTime());
//        reprintMainPOJO.setDate(ConfigCommonClass.getCurrentDateOnly());
//        setPrintLineLeft("Lott Date: "+ConfigCommonClass.getCurrentDateOnly());
//        int Count=0;
//        ArrayList<ReprintItem> reprintItemArrayList=new ArrayList<ReprintItem>();
//        setPrintLineLeft("========================");
//        for(SelectNumberMainPOJO selected_number:BaseActivity.selectNumberMainPOJOSArrayList) {
//            ReprintItem reprintItem=new ReprintItem();
//            if (selected_number.getSelected_game().equals("Single M")) {
//                if (selected_number.getSelected_number() != null
//                        && selected_number.getSelected_number().size() != 0) {
//                    reprintItem.setGame("SingleM");
//                    setBoldStyleSet();
//                    setPrintLineLeft("Game: SingleM");
//                    setStyleNormal();
//                    if(selected_number.getAdvanced().equals("1")) {
//                        setPrintLineLeft("Rate: " + selected_number.getRate() + " Adv:"
//                                + selected_number.getAdvanced_timeslot().size());
//                    }
//                    else{
//                        setPrintLineLeft("Rate: " + selected_number.getRate());
//                    }
//                    reprintItem.setRate(selected_number.getRate());
//                    reprintItem.setAdv(selected_number.getAdvanced_timeslot().size()+"");
//                    setPrintLineLeft("-------------------------");
//                    String time = "";
//                    int i = 0;
//                    for (Advanced_timeslot item :
//                            selected_number.getAdvanced_timeslot()) {
//                        if(selected_number.getAdvanced().equals("1")) {
//                            if (i == 0) {
//                                String k = getTimeSlotById(item.getAdv_time_slot_id());
//                                time = time + "/ " + k;
//                                i++;
//                            }
//                        }else {
//                            String k = getTimeSlotById(item.getAdv_time_slot_id());
//                            time = time + "/ " + k;
//                        }
//                    }
//
//                    setPrintLineLeft("LOT TIME: " + time);
//                    reprintItem.setLot_time(time);
//                    setBoldStyleSet();
//                    setPrintLineLeft("Mch/SR   NO   QTY");
//                    setStyleNormal();
//                    String s = "";
//
//                    for (Print_jsonPOJO item : PlayGameActivity.print_jsonPOJOSAddToCart_M) {
//                        s = s + "\n" + item.getLott_name() + "   " + item.getSr_no() + "/" + item.getIndex() + "  " + item.getBet();
//                    }
//                    setPrintLineLeft(BaseActivity.printSingleArray.get(Count));
//                    reprintItem.setString(BaseActivity.printSingleArray.get(Count));
//                    Count++;
//                    setPrintLineLeft("-------------------------");
//                    setBoldStyleSet();
//                    totatGrand=totatGrand+Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate())*selected_number.getAdvanced_timeslot().size();
//                    setPrintLineLeft("Total: " + Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate()));
//                    reprintItem.setTotal(""+ Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate()));
//                    setStyleNormal();
//                    setPrintLineLeft("-------------------------");
//                    BaseActivity.print_jsonPOJOS = new ArrayList<Print_jsonPOJO>();
//                    reprintMainPOJO.setSingleM();
//                    reprintItemArrayList.add(reprintItem);
//                }
//            }
//        }
//        reprintMainPOJO.setSingleM(reprintItemArrayList);
//        ArrayList<ReprintItem> reprintItemArrayListB=new ArrayList<ReprintItem>();
//
//        int Count1=0;
//        for(SelectNumberMainPOJO selected_number:BaseActivity.selectNumberMainPOJOSArrayListB) {
//            ReprintItem reprintItem=new ReprintItem();
//            if (selected_number.getSelected_game().equals("Single B")) {
//                if (selected_number.getSelected_number() != null
//                        && selected_number.getSelected_number().size() != 0) {
//
//                    setBoldStyleSet();
//                    setPrintLineLeft("Game: SingleB");
//                    reprintItem.setGame("SingleB");
//
//                    setStyleNormal();
//                    if(selected_number.getAdvanced().equals("1")) {
//                        setPrintLineLeft("Rate: " + selected_number.getRate() + " Adv:"
//                                + selected_number.getAdvanced_timeslot().size());
//                    }
//                    else{
//                        setPrintLineLeft("Rate: " + selected_number.getRate());
//                    }
//                    reprintItem.setRate(selected_number.getRate());
//                    reprintItem.setAdv(selected_number.getAdvanced_timeslot().size()+"");
//                    setPrintLineLeft("-------------------------");
//                    String time = "";
//                    int i = 0;
//                    for (Advanced_timeslot item :
//                            selected_number.getAdvanced_timeslot()) {
//                        if(selected_number.getAdvanced().equals("1")) {
//                            if (i == 0) {
//                                String k = getTimeSlotById(item.getAdv_time_slot_id());
//                                time = time + "/ " + k;
//                                i++;
//                            }
//                        }else {
//                            String k = getTimeSlotById(item.getAdv_time_slot_id());
//                            time = time + "/ " + k;
//                        }
//                    }
//                    setPrintLineLeft("LOT TIME: " + time);
//                    reprintItem.setLot_time(time);
//                    setBoldStyleSet();
//                    setPrintLineLeft("Mch/SR   NO   QTY");
//                    setStyleNormal();
//                    setPrintLineLeft(BaseActivity.printSingleArrayB.get(Count1));
//                    reprintItem.setString(BaseActivity.printSingleArrayB.get(Count1));
//                    Count1++;
//                    setPrintLineLeft("-------------------------");
//                    setBoldStyleSet();
//                    totatGrand=totatGrand+Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate())*selected_number.getAdvanced_timeslot().size();
//
//                    setPrintLineLeft("Total: " + Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate()));
//                    setPrintLineLeft("-------------------------");
//
//                    reprintItem.setTotal(Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate())+"");
//                    setStyleNormal();
//                    reprintItemArrayListB.add(reprintItem);
//
//                }
//            }
//        }
//        reprintMainPOJO.setSingleB(reprintItemArrayListB);
//        ArrayList<ReprintItem> reprintItemArrayListJodi=new ArrayList<ReprintItem>();
//
//        for( JodiMainPOJO  item1:BaseActivity.jodiMainPOJOPlayGameList)
//        {
//            ReprintItem reprintItem=new ReprintItem();
//
//            setBoldStyleSet();
//            setPrintLineLeft("Game: Jodi");
//            reprintItem.setGame("Jodi");
//            setStyleNormal();
//            setPrintLineLeft("Adv:"
//                    + item1.getAdvanced());
//            reprintItem.setAdv(item1.getAdvanced()+"");
//            setPrintLineLeft("-------------------------");
//            setBoldStyleSet();
//            setPrintLineLeft("Lot Time Mch/SR NO QTY");
//            setStyleNormal();
//            String s="";
//            for(Jodi_array_num item: item1.getJodi_selections())
//            {
//                s=s+"\n"+item.getTimeSlot()+" "+PlayGameActivity.getLottname(item.getLott_name())+"/"+PlayGameActivity.getSrNumber(item.getLott_name(),item.getIndex())+" "+item.getNumber();
//            }
//            reprintItem.setString(s);
//            setPrintLineLeft(s);
//            setPrintLineLeft("-------------------------");
//            setBoldStyleSet();
//            setPrintLineLeft("Combinations: "+item1.getCombination());
//            reprintItem.setCombination(item1.getCombination());
//            int totalJodi=Integer.parseInt(item1.getCombination())*Integer.parseInt(item1.getBete());
//            setPrintLineLeft("Total: "+item1.getCombination()+"X"+item1.getBete()+"="+totalJodi);
//            reprintItem.setTotal(""+item1.getCombination()+"X"+item1.getBete()+"="+totalJodi);
//            totatGrand=totatGrand+Integer.parseInt(item1.getTotal());
//            setStyleNormal();
//            reprintItemArrayListJodi.add(reprintItem);
//        }
//
//        reprintMainPOJO.setJodi(reprintItemArrayListJodi);
//        setBoldStyleSet();
//        setPrintLineLeft("Grand Total: "+totatGrand);
//        reprintMainPOJO.setGrand_total(""+totatGrand);
//        setStyleNormal();
//        setcode128(s1);
//        setNgxPrinter();
//        setNgxPrinter();
//        BaseActivity.last5JsonStr.add(reprintMainPOJO);
//        if(BaseActivity.last5JsonStr.size()>=5)
//        {
//            BaseActivity.last5JsonStr.remove();
//        }
//        sessionManager.setReprintData(BaseActivity.last5JsonStr);
//        BaseActivity.jodiMainPOJOPlayGame=null;
//        SingleMFragment.print_jsonPOJOSCP=new ArrayList<Print_jsonPOJO>();
//        SingleMFragment.print_jsonPOJOS=new ArrayList<Print_jsonPOJO>();
//        SingleBFragment.print_jsonPOJOS=new ArrayList<Print_jsonPOJO>();
//        try {
//            Thread.sleep(500);
//            Intent i=new Intent(context,PlayGameActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(i);
//        }
//        catch (InterruptedException ex) {
//            Log.d("YourApplicationName", ex.toString());
//        }
//    }
//    public  void setPrintFromAnotherActivity1(String reciptId)
//    {
//        reprintMainPOJO=new ReprintMainPOJO();
//        totatGrand=0;
//        String s1=reciptId;
//        setBoldStyleSet();
//        setPrintLineLeft("PLAY BOOK LUCKY COUPON");
//        setStyleNormal();
//        setPrintLineLeft(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
//        reprintMainPOJO.setAgents_name(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
//        setPrintLineLeft("Code No: "+s1);
//        reprintMainPOJO.setBarcode(s1);
//        setPrintLineLeft("Date: "+ ConfigCommonClass.getCurrentDateAndTime());
//        reprintMainPOJO.setDate(ConfigCommonClass.getCurrentDateOnly());
//        setPrintLineLeft("Lott Date: "+ConfigCommonClass.getCurrentDateOnly());
//        int Count=0;
//        ArrayList<ReprintItem> reprintItemArrayList=new ArrayList<ReprintItem>();
//        setPrintLineLeft("========================");
//        for(SelectNumberMainPOJO selected_number:BaseActivity.selectNumberMainPOJOSArrayList) {
//            ReprintItem reprintItem=new ReprintItem();
//            if (selected_number.getSelected_game().equals("Single M")) {
//                if (selected_number.getSelected_number() != null
//                        && selected_number.getSelected_number().size() != 0) {
//                    reprintItem.setGame("SingleM");
//                    setBoldStyleSet();
//                    setPrintLineLeft("Game: SingleM");
//                    setStyleNormal();
//                    setPrintLineLeft("Rate: " + selected_number.getRate() + " Adv:"
//                            + selected_number.getAdvanced_timeslot().size());
//                    reprintItem.setRate(selected_number.getRate());
//                    reprintItem.setAdv(selected_number.getAdvanced_timeslot().size()+"");
//                    setPrintLineLeft("---------------------------------------------");
//                    String time = "";
//                    int i = 0;
//                    for (Advanced_timeslot item :
//                            selected_number.getAdvanced_timeslot()) {
//                        if (i == 0) {
//                            String k = getTimeSlotById(item.getAdv_time_slot_id());
//                            time = time + "/ " + k;
//                            i++;
//                        }
//                    }
//                    setPrintLineLeft("LOT TIME: " + time);
//                    reprintItem.setLot_time(time);
//                    setBoldStyleSet();
//                    setPrintLineLeft("Mch/SR   NO   QTY");
//                    setStyleNormal();
//                    String s = "";
//
//                    for (Print_jsonPOJO item : PlayGameActivity.print_jsonPOJOSAddToCart_M) {
//                        s = s + "\n" + item.getLott_name() + "   " + item.getSr_no() + "/" + item.getIndex() + "  " + item.getBet();
//                    }
//                    setPrintLineLeft(BaseActivity.printSingleArray.get(Count));
//                    reprintItem.setString(BaseActivity.printSingleArray.get(Count));
//                    Count++;
//                    setPrintLineLeft("---------------------------------------------");
//                    setBoldStyleSet();
//                    totatGrand=totatGrand+Integer.parseInt(selected_number.getTotal());
//                    setPrintLineLeft("Total: " + Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate()));
//                    reprintItem.setTotal(""+ Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate()));
//                    setStyleNormal();
//                    setPrintLineLeft("---------------------------------------------");
//                    BaseActivity.print_jsonPOJOS = new ArrayList<Print_jsonPOJO>();
////                    reprintMainPOJO.setSingleM();
//                    reprintItemArrayList.add(reprintItem);
//                }
//            }
//        }
//        reprintMainPOJO.setSingleM(reprintItemArrayList);
//        ArrayList<ReprintItem> reprintItemArrayListB=new ArrayList<ReprintItem>();
//
//        int Count1=0;
//        for(SelectNumberMainPOJO selected_number:BaseActivity.selectNumberMainPOJOSArrayListB) {
//            ReprintItem reprintItem=new ReprintItem();
//            if (selected_number.getSelected_game().equals("Single B")) {
//                if (selected_number.getSelected_number() != null
//                        && selected_number.getSelected_number().size() != 0) {
//
//                    setBoldStyleSet();
//                    setPrintLineLeft("Game: SingleB");
//                    reprintItem.setGame("SingleB");
//
//                    setStyleNormal();
//                    setPrintLineLeft("Rate: " + selected_number.getRate() + " Adv:"
//                            + selected_number.getAdvanced_timeslot().size());
//                    reprintItem.setRate(selected_number.getRate());
//                    reprintItem.setAdv(selected_number.getAdvanced_timeslot().size()+"");
//
//                    setPrintLineLeft("---------------------------------------------");
//                    String time = "";
//                    int i = 0;
//                    for (Advanced_timeslot item : selected_number.getAdvanced_timeslot()) {
//                        if (i == 0) {
//                            String k = getTimeSlotById(item.getAdv_time_slot_id());
//                            time = time + "/ " + k;
//                            i++;
//                        }
//                    }
//
//                    setPrintLineLeft("LOT TIME: " + time);
//                    reprintItem.setLot_time(time);
//                    setBoldStyleSet();
//                    setPrintLineLeft("Mch/SR   NO   QTY");
//                    setStyleNormal();
//                    setPrintLineLeft(BaseActivity.printSingleArrayB.get(Count1));
//                    reprintItem.setString(BaseActivity.printSingleArrayB.get(Count1));
//                    Count1++;
//                    setPrintLineLeft("---------------------------------------------");
//                    setBoldStyleSet();
//                    totatGrand=totatGrand+Integer.parseInt(selected_number.getTotal());
//
//                    setPrintLineLeft("Total: " + Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate()));
//                    setPrintLineLeft("---------------------------------------------");
//
//                    reprintItem.setTotal(Integer.parseInt(selected_number.getSub_total())*Integer.parseInt(selected_number.getRate())+"");
//                    setStyleNormal();
//                    reprintItemArrayListB.add(reprintItem);
//
//                }
//            }
//        }
//        reprintMainPOJO.setSingleB(reprintItemArrayListB);
//        ArrayList<ReprintItem> reprintItemArrayListJodi=new ArrayList<ReprintItem>();
//
//        for( JodiMainPOJO  item1:BaseActivity.jodiMainPOJOPlayGameList)
//        {
//            ReprintItem reprintItem=new ReprintItem();
//
//            setBoldStyleSet();
//            setPrintLineLeft("Game: Jodi");
//            reprintItem.setGame("Jodi");
//            setStyleNormal();
//            setPrintLineLeft("Adv:"
//                    + item1.getAdvanced());
//            reprintItem.setAdv(item1.getAdvanced()+"");
//            setPrintLineLeft("---------------------------------------------");
//            setBoldStyleSet();
//            setPrintLineLeft("Lot Time Mch/SR NO QTY");
//            setStyleNormal();
//            String s="";
//            for(Jodi_array_num item: item1.getJodi_selections())
//            {
//                s=s+"\n"+item.getTimeSlot()+" "+PlayGameActivity.getLottname(item.getLott_name())+"/"+PlayGameActivity.getSrNumber(item.getLott_name(),item.getIndex())+" "+item.getNumber();
//            }
//            reprintItem.setString(s);
//            setPrintLineLeft(s);
//            setPrintLineLeft("---------------------------------------------");
//            setBoldStyleSet();
//            setPrintLineLeft("Combinations: "+item1.getCombination());
//            reprintItem.setCombination(item1.getCombination());
//            int totalJodi=Integer.parseInt(item1.getCombination())*Integer.parseInt(item1.getBete());
//            setPrintLineLeft("Total: "+item1.getCombination()+"X"+item1.getBete()+"="+totalJodi);
//            reprintItem.setTotal(""+item1.getCombination()+"X"+item1.getBete()+"="+totalJodi);
//            totatGrand=totatGrand+Integer.parseInt(item1.getTotal());
//
//            setStyleNormal();
//            reprintItemArrayListJodi.add(reprintItem);
//        }
//        reprintMainPOJO.setJodi(reprintItemArrayListJodi);
//        setBoldStyleSet();
//        setPrintLineLeft("Grand Total: "+totatGrand);
//        reprintMainPOJO.setGrand_total(""+totatGrand);
//        setStyleNormal();
//        //setNgxPrinter();
//        setPrintLineLeft(s1);
//        setcode128(reciptId);
//        setNgxPrinter();
//
//        BaseActivity.last5JsonStr.add(reprintMainPOJO);
//        if(BaseActivity.last5JsonStr.size()>=5)
//        {
//            BaseActivity.last5JsonStr.remove();
//        }
//        sessionManager.setReprintData(BaseActivity.last5JsonStr);
//        BaseActivity.jodiMainPOJOPlayGame=null;
//        SingleMFragment.print_jsonPOJOSCP=new ArrayList<Print_jsonPOJO>();
//        SingleMFragment.print_jsonPOJOS=new ArrayList<Print_jsonPOJO>();
//        SingleBFragment.print_jsonPOJOS=new ArrayList<Print_jsonPOJO>();
//        try {
//            Thread.sleep(10000);
//            Intent i=new Intent(context,PlayGameActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(i);
//
//        }
//        catch (InterruptedException ex) {
//            Log.d("YourApplicationName", ex.toString());
//        }
//    }
//    public  void setRePrintFromAnotherActivity(ReprintMainPOJO reprintMainPOJOS)
//    {
//        // reprintMainPOJO=new ReprintMainPOJO();
//        totatGrand=0;
//        String s1=reprintMainPOJOS.getBarcode();
//        setBoldStyleSet();
//        setPrintLineLeft("PLAY BOOK LUCKY COUPON");
//        setStyleNormal();
//        setPrintLineLeft(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
//        //reprintMainPOJO.setAgents_name(sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
//        setPrintLineLeft("Code No: "+s1);
//        //reprintMainPOJO.setBarcode(s1);
//        setPrintLineLeft("Date: "+ ConfigCommonClass.getCurrentDateAndTime());
//        //  reprintMainPOJO.setDate(ConfigCommonClass.getCurrentDateOnly());
//        setPrintLineLeft("Lott Date: "+reprintMainPOJOS.getDate());
//        int Count=0;
//        //ArrayList<ReprintItem> reprintItemArrayList=new ArrayList<ReprintItem>();
//        setPrintLineLeft("========================");
//        for(ReprintItem itemMain:reprintMainPOJOS.getSingleM()) {
//            // ReprintItem reprintItem=new ReprintItem();
//
//            // reprintItem.setGame("SingleM");
//            setBoldStyleSet();
//            setPrintLineLeft("Game: SingleM");
//            setStyleNormal();
//            setPrintLineLeft("Rate: " + itemMain.getRate() + " Adv:"
//                    + itemMain.getAdv());
//
//            setPrintLineLeft("---------------------------------------------");
//            String time = "";
//            int i = 0;
////
//            setPrintLineLeft("LOT TIME: " + itemMain.getLot_time());
//            setBoldStyleSet();
//            setPrintLineLeft("Mch/SR   NO   QTY");
//            setStyleNormal();
//            String s = "";
//
//
//            setPrintLineLeft(itemMain.getString());
//            setPrintLineLeft("---------------------------------------------");
//            setBoldStyleSet();
//            setPrintLineLeft("Total: " + itemMain.getTotal());
//            setStyleNormal();
//            setPrintLineLeft("---------------------------------------------");
//
//        }
//
//        int Count1=0;
//        for(ReprintItem itemMain:reprintMainPOJOS.getSingleB()) {
//            // ReprintItem reprintItem=new ReprintItem();
//
//            // reprintItem.setGame("SingleM");
//            setBoldStyleSet();
//            setPrintLineLeft("Game: SingleM");
//            setStyleNormal();
//            setPrintLineLeft("Rate: " + itemMain.getRate() + " Adv:"
//                    + itemMain.getAdv());
//
//            setPrintLineLeft("---------------------------------------------");
//            String time = "";
//            int i = 0;
////
//            setPrintLineLeft("LOT TIME: " + itemMain.getLot_time());
//            setBoldStyleSet();
//            setPrintLineLeft("Mch/SR   NO   QTY");
//            setStyleNormal();
//            String s = "";
//
//
//            setPrintLineLeft(itemMain.getString());
//            setPrintLineLeft("---------------------------------------------");
//            setBoldStyleSet();
//            setPrintLineLeft("Total: " + itemMain.getTotal());
//            setStyleNormal();
//            setPrintLineLeft("---------------------------------------------");
//
//        }
//
//        for( ReprintItem  itemJodi:reprintMainPOJOS.getJodi())
//        {
//
//            setBoldStyleSet();
//            setPrintLineLeft("Game: Jodi");
//            setStyleNormal();
//            setPrintLineLeft("Adv:"
//                    + itemJodi.getAdv());
//            setPrintLineLeft("---------------------------------------------");
//            setBoldStyleSet();
//            setPrintLineLeft("Lot Time Mch/SR NO QTY");
//            setStyleNormal();
//            String s="";
//
//            setPrintLineLeft(itemJodi.getString());
//            setPrintLineLeft("---------------------------------------------");
//            setBoldStyleSet();
//            setPrintLineLeft("Combinations: "+itemJodi.getCombination());
//            //int totalJodi=Integer.parseInt(itemJodi.getCombination())*Integer.parseInt(item1.getBete());
//            setPrintLineLeft("Total: "+itemJodi.getTotal());
//
//            setStyleNormal();
//        }
//        setBoldStyleSet();
//        setPrintLineLeft("Grand Total: "+reprintMainPOJOS.getGrand_total());
//        //reprintMainPOJO.setGrand_total(""+totatGrand);
//        setStyleNormal();
//        //setNgxPrinter();
//        setcode128(s1);
//        // setPrintLineCenter(s1);
//        setNgxPrinter();
//
//
//
//        try {
//            Thread.sleep(10000);
//            Intent i=new Intent(context,PlayGameActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(i);
//
//        }
//        catch (InterruptedException ex) {
//            Log.d("YourApplicationName", ex.toString());
//        }
//    }
//    String getTimeSlotById(String s)
//    {
//
//        for(ResultPOJO item:sessionManager.getTimeSlot().getResult())
//        {
//            if(item.getId().equals(s))
//            {
//                return item.getFrom_time();
//            }
//        }
//        return null;
//    }
    public void setAlignment(String str)
    {
     //   String str = mEdit.getText().toString();
        if (str.length() == 0) {
            Toast.makeText(context, "Nothing to print", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            switch (alignment) {
                case "LEFT":
                    ngxPrinter.printText(str, Alignments.LEFT, selectedFontSize);
                    break;
                case "CENTER":
                    ngxPrinter.printText(str, Alignments.CENTER, selectedFontSize);
                    break;
                case "RIGHT":
                    ngxPrinter.printText(str, Alignments.RIGHT, selectedFontSize);
                    break;
                default:
                    ngxPrinter.printText(str, Alignments.LEFT, selectedFontSize);
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void setNgxPrinter()
    {
        try {
            ngxPrinter.lineFeed(1);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setExmpPrintInDiffLang()
    {
        printKannadaBill();
        printHindiBill();
        printEnglishBill();
    }
    public void setBoldStyleSet()
    {
        isBoldStyleSet = true;
        try {

            ngxPrinter.setStyleBold();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setPrintLine(String s)
    {
        ngxPrinter.printText(s, Alignments.CENTER, 30);
    }
    public void setPrintLineRight(String s)
    {
        ngxPrinter.printText(s, Alignments.RIGHT, 30);
    }
    public void setPrintLineLeft(String s)
    {
        ngxPrinter.printText(s, Alignments.LEFT, 30);
    }
    public void printSampleBill()
    {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy '@'HH:mm", Locale.getDefault());
            Date date = new Date();
            //"DT: " + format.format(date) + "\n" ;
            ngxPrinter.setDefault();
            ngxPrinter.setStyleDoubleWidth();
            ngxPrinter.printText("CASH BILL", Alignments.CENTER, 30);
            ngxPrinter.setStyleNormal();
            ngxPrinter.printText("DT: " + format.format(date), Alignments.LEFT, 24);
            ngxPrinter.printText("BillNumber: 00000001", Alignments.LEFT, 24);
            ngxPrinter.lineFeed(1);
            ngxPrinter.setStyleBold();
            ngxPrinter.printText("Item    Qty    Price   Total", Alignments.LEFT, 24);
            ngxPrinter.setStyleNormal();
            ngxPrinter.printText("------------------------------", Alignments.LEFT, 24);
            ngxPrinter.printText("Brown  3PC    35.00    105.00", Alignments.LEFT, 26);
            ngxPrinter.printText("LP 6 Oil SHA\n\t\t\t\t\t\t\t9PC    35.00    315.00", Alignments.LEFT, 26);
            ngxPrinter.printText("WC 3 IN 1\n\t\t\t\t\t\t 5PC    25.00    125.00", Alignments.LEFT, 26);
            ngxPrinter.lineFeed();
            ngxPrinter.printText("------------------------------", Alignments.LEFT, 24);
            ngxPrinter.setStyleDoubleHeight();
            ngxPrinter.printText("TOTAL          Rs. 545.00", Alignments.RIGHT, 30);
            ngxPrinter.setStyleNormal();
            ngxPrinter.printText("------------------------------", Alignments.LEFT, 24);
            ngxPrinter.printText("PAID Rs. 550.00", Alignments.LEFT, 26);
            ngxPrinter.printText("CHANGE Rs. 5.00", Alignments.LEFT, 26);
            ngxPrinter.lineFeed(5);
            ngxPrinter.setDefault();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setStyleDoubleWidth()
    {
        try {
            ngxPrinter.setStyleDoubleWidth();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setRegulerStyle()
    {
        isBoldStyleSet = false;

    }
    public void setStyleDoubleHeight()
    {
        try {
            ngxPrinter.setStyleDoubleHeight();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setStyleNormal()
    {
        try {
            ngxPrinter.setStyleNormal();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setITF()
    {
        try {
            ngxPrinter.printBarcode("789123456072", NGXBarcodeCommands.ITF, 100, 380);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public  void setcode128(String str)
    {
        try {
            ngxPrinter.printBarcode(str, NGXBarcodeCommands.CODE128, 100, 380);
            //setPrintLine(str);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public  void setPDF417()
    {
        try {
            ngxPrinter.printBarcode("7891ABCabc()#6072", NGXBarcodeCommands.CODE128, 100, 380);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setcodebar()
    {
        try {
            String str = "A12588963B";
            ngxPrinter.printBarcode(str, NGXBarcodeCommands.CODABAR, 100, 380);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setQRcode()
    {
        try {
            ngxPrinter.printBarcode("http://ngxtech.com", NGXBarcodeCommands.QRCode, 200, 200);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setCode39(String Str)
    {
        try {
          //  String Str = mEdit.getText().toString();
            if (Str.length() == 0) {
                ngxPrinter.printBarcode("ABC123$ -", NGXBarcodeCommands.CODE39, 100, 380);
                ngxPrinter.lineFeed(4);
                ngxPrinter.printBarcode("1478523697894561230", NGXBarcodeCommands.CODE39, 100, 384);
                ngxPrinter.lineFeed(4);
            } else
                ngxPrinter.printBarcode(Str, NGXBarcodeCommands.CODE39, 100, 380);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setEAN8()
    {
        try {
            ngxPrinter.printBarcode("15896347", NGXBarcodeCommands.JAN8_EAN8, 100, 380);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setEAN13()
    {
        try {
            ngxPrinter.printBarcode("15896347", NGXBarcodeCommands.JAN13_EAN13, 100, 380);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public  void setPrintLog(String filepath)
    {
        try {
            ngxPrinter.printLogo(filepath);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE
                && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Uri fileUri = Uri.parse(picturePath);
                if (fileUri != null) {
                    try {
                        ngxPrinter.printLogo(fileUri.getPath());
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context,
                            "media handler not available, choose another image",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Function to display popup with options to select Image.
     */

    public void dialogBox() {
        CharSequence storage[] = new CharSequence[]{"Select from Gallery"/*, "Select from different location"*/};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick a storage");
        builder.setItems(storage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                      //  context.startActivityForResult(i, RESULT_LOAD_IMAGE);
//						loadImage(i);
                        break;
                    /*case 1:
                        mFileSel = new FileSelector(context, FileOperation.LOAD, mLoadFileListener, mFileFilter);
                        mFileSel.show();
                        break;*/
                }
            }
        });
        builder.show();
    }

    public void print(String str) {
        try {
           // String str = mEdit.getText().toString();
            if (str.length() == 0) {
              //  Toast.makeText(context, "Nothing to print", Toast.LENGTH_LONG).show();
                return;
            }
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "Fonts/DroidSansMono.ttf");
            TextPaint tp = new TextPaint();
            tp.setColor(Color.BLACK);
            tp.setTextSize(selectedFontSize);
            if (isBoldStyleSet)
                tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
            else
                tp.setTypeface(tf);
            Log.i("NGX", "printing start");
            switch (alignment) {
                case "LEFT":
                    ngxPrinter.printUnicodeText(str, Layout.Alignment.ALIGN_NORMAL, tp);
                    break;
                case "CENTER":
                    ngxPrinter.printUnicodeText(str, Layout.Alignment.ALIGN_CENTER, tp);
                    break;
                case "RIGHT":
                    ngxPrinter.printUnicodeText(str, Layout.Alignment.ALIGN_OPPOSITE, tp);
                    break;
                default:
                    ngxPrinter.printUnicodeText(str, Layout.Alignment.ALIGN_NORMAL, tp);
                    break;
            }
            ngxPrinter.lineFeed(1);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String shortBillSample() {
        String separator = "-----------------------------\n";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy '@'HH:mm", Locale.getDefault());
        Date date = new Date();
        String str = "DT: " + format.format(date) + "\n" +
                separator +
                "Item        Qty    Price    Total\n" +
                separator;
        for (int i = 0; i < 20; i++) {
            str += "Brown         3PC    35.00    105.00\n" +
                    "LP 6 Oil SHA  9PC    30.00    270.00\n" +
                    "WC 3 IN 1     5PC    25.00    125.00\n" +
                    "WC GENTLE S  36PC    35.00    1260.00\n";
        }
        str += separator +
                "Total         1760.00";
        return str;
    }

    public String longBillSample() {
        return "--------------------------------\n" +
                "		ITEM           QTY	   PRICE\n" +
                "--------------------------------\n" +
                "APASARA PENCILS      250	100\n" +
                "BILLING MACHINES  	  15	100\n" +
                "NAIL PAINT PINK     2600   100\n" +
                "PEPSODENT PASTE      100	104\n" +
                "SAMSUNG GRAND         29   100\n" +
                "BEAUTY TOOLS      	  35   100\n" +
                "NAIL POLISHES         70   100\n" +
                "EYE SHADOWS       	  10   108\n" +
                "ICE CREAM             16   100\n" +
                "VIM BAR SOAPS         10	110\n" +
                "FACE WASH PONDS       10   111\n" +
                "FACE PACK CREAM  	  10   112\n" +
                "COLGATE TH PST		 150 	100\n" +
                "HAIR CLR PWDR 	  	 156	100\n" +
                "KING SIZE NT BOOK 	   5	103.5\n" +
                "SML SIZE NOTE BOO    250	 10\n" +
                "VEGETABLES,FRUITS    100	117\n" +
                "WATERMELON JUICE      10	118\n" +
                "APPLE JUICE      	 250    50\n" +
                "APASARA PENCILS      100   120\n" +
                "--------------------------------\n" +
                "TOTAL AMOUNT : 	   382307.50\n" +
                "--------------------------------\n" +
                "            THANK YOU        \n";
    }

    public String getKannadaText() {
        return "ನಾವು ಮತ್ತು ನಮ್ಮ ದೇಶ\n" +
                "ನಾವೆಲ್ಲ ಭಾರತೀಯರು. ನಾವು ನಮ್ಮ ದೇಶದ ಯಾವುದೇ ಪ್ರಾಂತ್ಯದ ನಿವಾಸಿಗಳಾಗಿರಬಹುದು ಅಥವಾ ಯಾವುದೇ ಜಾತಿ ಅಥವಾ ಮತಕ್ಕೆ ಸೇರಿರಬಹುದು, ಅಥವಾ ಯಾವುದೇ ಭಾಷೆಯನ್ನಾಡುವವರಾಗಿರಬಹುದು, ನಾವೆಲ್ಲಾ ಭಾರತೀಯರು.\n" +
                // "\n" +
                "ನಮ್ಮ ಸಂಸ್ಕೃತಿ ಪುರಾತನವಾದದ್ದು. ನಮ್ಮ ದೇಶದಲ್ಲಿ ಅನೇಕ ಮಹಾನ್ ವ್ಯಕ್ತಿಗಳು, ಸಾದು ಸಂತರು, ತತ್ವ ಜ್ಞಾನಿಗಳು, ದೇಶ ಭಕ್ತರು, ಕವಿಗಳು, ಸಾಹಿತಿಗಳು, ಗಣಿತ, ವಿಜ್ಞಾನ, ಜ್ಯೋತಿಷ್ಯ, ಅಯುರ್ವೇದ ಮುಂತಾದ ಅನೇಕ ವಿಚಾರಗಳಲ್ಲಿ ಪಂಡಿತರು, ವೀರರು, ಶಿಲ್ಪ, ಸಂಗೀತ, ನಾಟ್ಯ, ನಾಟಕ, ಕುಶಲ ಕಲೆಗಳಲ್ಲಿ ಪರಿಣಿತರು ಹುಟ್ಟಿ ನಮ್ಮ ದೇಶದ ಹಾಗೂ ಮಾನವ ಕಲ್ಯಾಣಕ್ಕಾಗಿ ದುಡಿದಿದ್ದಾರೆ, ಶ್ರಮಿಸಿದ್ದಾರೆ. ಅವರು ದೇಶಕ್ಕೆ ವಿವಿಧ ಕೊಡುಗೆಗಳನ್ನು ಕೊಟ್ಟು ಮಹದುಪಕಾರ ಮಾಡಿದ್ದಾರೆ.\n" +
                //"\n" +
                "ಅನೇಕ ದೇಶಭಕ್ತರ ಸತತ ಹೋರಾಟದ ಫಲವಾಗಿ ನಮ್ಮ ದೇಶವು ೧೯೪೭ನೇ ಇಸವಿ ಅಗಸ್‌್ಟ ೧೫ರಂದು ಸ್ವಾತಂತ್ರ್ಯ ಗಳಿಸಿತು. ಅನೇಕರು ಈ ಹೋರಾಟದಲ್ಲಿ ತಮ್ಮ ಪ್ರಾಣವನ್ನೇ ಅರ್ಪಿಸಿದರು. ಮಹಾತ್ಮ ಗಾಂಧೀಜಿಯವರ ಪಾತ್ರವನ್ನು ಇಲ್ಲಿ ಸ್ಮರಿಸಬಹುದು. ಅವರು ಈ ಹೋರಾಟದಲ್ಲಿ ಹಿರಿಯ ನಾಯಕರಾಗಿದ್ದರು. ನಾವು ಸಾಧಿಸಬೇಕಾದ ಗುರಿ ಎಷ್ಟು ಒಳ್ಳೆಯದೋ, ಅದನ್ನು ಸಾಧಿಸುವ ಮಾರ್ಗವೂ ಕೂಡ ಅಷ್ಟೇ ಒಳ್ಳೆಯದಾಗಿರಬೇಕೆಂಬುದೇ ಅವರ ದೃಢ ಮತವಾಗಿತ್ತು. ಅದರಂತೆಯೇ ಅವರು ಸ್ವಾತಂತ್ರ‍್ಯಕ್ಕಾಗಿ ನಡಸಿದ ಹೋರಾಟದಲ್ಲಿ ಅಹಿಂಸಾ ತತ್ವವನ್ನು ಅನುಸರಿಸಿ ಇತರರಿಗೂ ಅದನ್ನೇ ಬೋಧಿಸಿದರು ಮತ್ತು ಮಾರ್ಗದರ್ಶನ ನೀಡಿದರು.\n" +
                //"\n" +
                "ಇದುವರೆಗೂ ನಮ್ಮ ದೇಶವು ಅನೇಕ ವಿಚಾರಗಳಲ್ಲಿ ಪ್ರಗತಿ ಹೊಂದಿದೆ. ಇನ್ನೂ ಅಗಬೇಕಾದದ್ದು ಅಪಾರವಾಗಿದೆ. ಪ್ರತಿಯೊಬ್ಬರೂ (ಮಕ್ಕಳು, ಯುವಕರು, ಹಿರಿಯರು) ಅವರವರ ಸ್ಥಾನಮಾನಕ್ಕೆ ತಕ್ಕಂತೆ, ದೇಶಾಭಿಮಾನದಿಂದಲೂ, ಒಗ್ಗಟ್ಟನಿಂದಲೂ, ಪ್ರೀತಿ ಸೌಹಾರ್ದಗಳಿಂದಲೂ, ಪ್ರಾಮಾಣಿಕತೆಯಿಂದಲೂ, ಕಾರ್ಯ ನಿಪುಣತೆಯಿಂದಲೂ, ವಿವೇಚನೆಯಿಂದಲೂ, ನಮ್ಮ ರಾಷ್ಟೃದ ರಕ್ಷಣೆ ಮತ್ತು ಪ್ರಗತಿಗಾಗಿ ದುಡಿಯಬೇಕು. ಅಲ್ಲದೆ, ಈ ಕಾರ್ಯಗಳಲ್ಲಿ ನಿರತರಾದವರಿಗೆ ನೆರವು ನೀಡಬೇಕು. ಇದು ದೇಶಕ್ಕೆ ಒಳಿತು, ನಮಗೂ ಒಳಿತು.";
    }

    public String getHindiText() {
        return "कम्प्यूटर, मूल रूप से, नंबरों से सम्बंध रखते हैं। ये प्रत्येक अक्षर और वर्ण के लिए एक नंबर निर्धारित करके अक्षर और वर्ण संग्रहित करते हैं। यूनिकोड का आविष्कार होने से पहले, ऐसे नंबर देने के लिए सैंकडों विभिन्न संकेत लिपि प्रणालियां थीं। किसी एक संकेत लिपि में पर्याप्त अक्षर नहीं हो सकते हैं : उदाहरण के लिए, यूरोपिय संघ को अकेले ही, अपनी सभी भाषाऒं को कवर करने के लिए अनेक विभिन्न संकेत लिपियों की आवश्यकता होती है। अंग्रेजी जैसी भाषा के लिए भी, सभी अक्षरों, विरामचिन्हों और सामान्य प्रयोग के तकनीकी प्रतीकों हेतु एक ही संकेत लिपि पर्याप्त नहीं थी।\n";
    }

    public String getEnglishText() {
        return "India, officially the Republic of India is a country in South Asia. It is the seventh-largest country by area, the second-most populous country (with over 1.2 \n" +
                "billion people), and the most populous democracy in the world. Bounded by the Indian Ocean on the south, the Arabian Sea on the south-west, and the Bay of Bengal \n" +
                "on the south-east, it shares land borders with Pakistan to the west;[d] China, Nepal, and Bhutan to the north-east; and Myanmar (Burma) and Bangladesh to the \n" +
                "east. In the Indian Ocean, India is in the vicinity of Sri Lanka and the Maldives; in addition, India's Andaman and Nicobar Islands share a maritime border with" +
                "Thailand and Indonesia.";
    }

    public String getTeluguText() {
        return "జరిగిందంతా చూస్తూ \nఎరగనట్లు పడి ఉండగ \nసాక్షీ భూతుణ్ణి గాను \nసాక్షాత్తూ మానవుణ్ణి";
    }

    public String getTamilText() {
        return "யூனிக்கோடு எந்த இயங்குதளம் ஆயினும், எந்த நிரல் ஆயினும், எந்த மொழி ஆயினும் ஒவ்வொரு எழுத்துக்கும் தனித்துவமான எண் ஒன்றை வழங்குகிறது.";
    }

    public String getAllLanguageText() {
        return "ಕನ್ನಡ ಮುದ್ರಣ\n" +
                getKannadaText() + "\n\n" +
                "हिंदी मुद्रण\n" +
                getHindiText() + "\n\n" +
                "English Print\n" +
                getEnglishText() + "\n\n" +
                "తెలుగు ప్రింటింగ్\n" +
                getTeluguText() + "\n\n" +
                "தமிழ் அச்சிடுதல்\n" +
                getTamilText() + "\n";
    }

    public void printKannadaBill() {
       /* if (mBtp.getState() != BluetoothPrinter.STATE_CONNECTED) {
            Toast.makeText(context, "Printer is not connected", Toast.LENGTH_SHORT).show();
            return;
        }*/
        String separator = "--------------------------------";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Fonts/DroidSansMono.ttf");

        Bitmap bmp = getBitmapFromAssets(fileName);
        ngxPrinter.addImage(bmp);
        bmp.recycle();

        TextPaint tp = new TextPaint();

        tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
        //tp.setTypeface(tf);
        tp.setTextSize(30);
        tp.setColor(Color.BLACK);
        ngxPrinter.addText("ನಗದು ಬಿಲ್ಲು", Layout.Alignment.ALIGN_CENTER, tp);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ದಿನಾಂಕ: 31-05-2017  ಬಿಲ್ ಸಂಖ್ಯೆ: 001\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("ಹೆಸರು: ವಿನಾಯಕ\n");
        stringBuilder.append("ಸ್ಥಳ: ಬೆಂಗಳೂರು\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("ವಿವರಣೆ        ಪ್ರಮಾಣ   ದರ    ಮೊತ್ತ\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("ರೆನಾಲ್ಡ್ಸ್ ಪೆನ್        2   10     20\n");
        stringBuilder.append("ನಟರಾಜ್ ಎರೇಸರ್    10    5     50\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("ಒಟ್ಟು ಐಟಂಗಳು: 2       ಮೊತ್ತ:  66.50\n");
        stringBuilder.append("ಒಟ್ಟು ಪ್ರಮಾಣ: 12   ವ್ಯಾಟ್ ಮೊತ್ತ: 3.50\n");
        stringBuilder.append("                  -------------");
        tp.setTextSize(20);
      //  tp.setTypeface(tf);
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

        stringBuilder.setLength(0);
        stringBuilder.append("      ನಿವ್ವಳ ಮೊತ್ತ: 70.00\n");
        tp.setTextSize(25);
        //tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

        stringBuilder.setLength(0);
        stringBuilder.append("ಪಾವತಿ ಮೋಡ್: ನಗದು\n\n\n");
        tp.setTextSize(20);
       // tp.setTypeface(tf);
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
        try {
            ngxPrinter.print();
            // ngxPrinter.printUnicodeText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL,tp);
            ngxPrinter.lineFeed(2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printHindiBill() {
       /* if (mBtp.getState() != BluetoothPrinter.STATE_CONNECTED) {
            Toast.makeText(this, "Printer is not connected", Toast.LENGTH_SHORT).show();
            return;
        }*/
        String separator = "--------------------------------";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Fonts/DroidSansMono.ttf");

        Bitmap bmp = getBitmapFromAssets(fileName);
        ngxPrinter.addImage(bmp);
        bmp.recycle();

        TextPaint tp = new TextPaint();
        tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
        tp.setTextSize(30);
        tp.setColor(Color.BLACK);
        ngxPrinter.addText("नकद बिल", Layout.Alignment.ALIGN_CENTER, tp);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("दिनांक: 31-05-2017    बिल संख्या: 001\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("नाम: विनायक\n");
        stringBuilder.append("स्थान: बैंगलोर\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("विवरण         मात्रा     मूल्य     रकम\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("रेनॉल्ड्स पेन      2      10      20\n");
        stringBuilder.append("नटराज इरेज़र     10      5      50\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("कुल सामान: 2            रकम: 66.50\n");
        stringBuilder.append("कुल मात्रा :12         वैट रकम:  3.50\n");
        stringBuilder.append("                  --------------");
        tp.setTextSize(20);
       // tp.setTypeface(tf);
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

        stringBuilder.setLength(0);
        stringBuilder.append("            नेट रकम: 70.00");
        tp.setTextSize(25);
      //  tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

        stringBuilder.setLength(0);
        stringBuilder.append("भुगतान मोड: नकद\n\n\n");
        tp.setTextSize(20);
       // tp.setTypeface(tf);
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

        try {
            ngxPrinter.print();
            ngxPrinter.lineFeed(2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void printEnglishBill() {
        /*if (mBtp.getState() != BluetoothPrinter.STATE_CONNECTED) {
            Toast.makeText(context, "Printer is not connected", Toast.LENGTH_SHORT).show();
            return;
        }*/
        String separator = "--------------------------------";
      //  Typeface tf = Typeface.createFromAsset(context.getAssets(), "Fonts/DroidSansMono.ttf");

//        Bitmap bmp = getBitmapFromAssets(fileName);
//        ngxPrinter.addImage(bmp);
//        bmp.recycle();

        TextPaint tp = new TextPaint();
       // tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
        tp.setTextSize(30);
        tp.setColor(Color.BLACK);
        ngxPrinter.addText("Cash Bill", Layout.Alignment.ALIGN_CENTER, tp);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: 31-05-2017  Bill No: 001\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("Name: Vinayak\n");
        stringBuilder.append("Place: Bangalore\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("Particulars    Qty   Rate    Amt\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("Reynolds Pen     2     10     20\n");
        stringBuilder.append("Nataraj Eraser  10      5     50\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("Tot Items: 2      Amount: 66.50\n");
        stringBuilder.append("Tot Qty  :12     Vat Amt:  3.50\n");
        stringBuilder.append("                  -------------");
        tp.setTextSize(20);
       // tp.setTypeface(tf);
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

        stringBuilder.setLength(0);
        stringBuilder.append("           Net Amt: 70.00");
        tp.setTextSize(25);
       // tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);


        stringBuilder.setLength(0);
        stringBuilder.append("Payment Mode: CASH\n\n\n");
        tp.setTextSize(20);
       // tp.setTypeface(tf);
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
        try {
            ngxPrinter.print();
            // ngxPrinter.printUnicodeText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL,tp);
            ngxPrinter.lineFeed(2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void printEnglishBill1(String ac_no,String acc_name,String Add,String amount,String strDate) {
        /*if (mBtp.getState() != BluetoothPrinter.STATE_CONNECTED) {
            Toast.makeText(context, "Printer is not connected", Toast.LENGTH_SHORT).show();
            return;
        }*/
        String separator = "------------------------------------------";
        //  Typeface tf = Typeface.createFromAsset(context.getAssets(), "Fonts/DroidSansMono.ttf");

//        Bitmap bmp = getBitmapFromAssets(fileName);
//        ngxPrinter.addImage(bmp);
//        bmp.recycle();

        TextPaint tp = new TextPaint();
        // tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
        tp.setTextSize(30);
        tp.setColor(Color.BLACK);

        ngxPrinter.addText("Payment Receipt", Layout.Alignment.ALIGN_CENTER, tp);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date : "+  strDate + "\n");
        stringBuilder.append("Account No : "+ ac_no+"\n");
        stringBuilder.append(separator);
        stringBuilder.append("\n");
        stringBuilder.append("Name :" +  acc_name+" \n");
        stringBuilder.append("Address : "+ Add+"\n");
        stringBuilder.append(separator);

        tp.setTextSize(25);
        // tp.setTypeface(tf);
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

        stringBuilder.setLength(0);
        stringBuilder.append("Net Amt : "+ amount);
        tp.setTextSize(25);
        // tp.setTypeface(Typeface.create(tf, Typeface.BOLD));
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

        stringBuilder.setLength(0);
        stringBuilder.append("Payment Mode : CASH\n\n");
        tp.setTextSize(25);
        // tp.setTypeface(tf);
        ngxPrinter.addText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
        try {
            ngxPrinter.print();
            // ngxPrinter.printUnicodeText(stringBuilder.toString(), Layout.Alignment.ALIGN_NORMAL,tp);
            ngxPrinter.lineFeed(2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = context.getAssets();

        InputStream istr = null;
        try {
            istr = assetManager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Bitmap bitmap = ;
        //mBtp.getMaxImgPrintWidth();
        return BitmapFactory.decodeStream(istr);
    }
}
