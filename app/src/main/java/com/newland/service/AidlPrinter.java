package com.newland.service;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RemoteException;

import com.jl.Ddi;
import com.shanxixinhe.application.AidlApplication;
import com.newland.aidl.printer.AidlPrinterListener;

/**
 * Created by fu on 2018/3/30.
 */

public class AidlPrinter extends com.newland.aidl.printer.AidlPrinter.Stub {

    private Ddi hal;

    public AidlPrinter () {
        hal = AidlApplication.getinstanceDdi();
    }

    @Override
    public void addText(Bundle format, String text) throws RemoteException {
            hal.ddi_prnt_init(100);
            hal.ddi_prnt_printf(text+"\n");
    }

    @Override
    public void paperSkip(int line) throws RemoteException {

    }

    @Override
    public void addPicture(Bundle format, Bitmap bitmap) throws RemoteException {

    }

    @Override
    public void addBarCode(Bundle format, String barCode) throws RemoteException {
        hal.ddi_prnt_esc(new byte[]{0x1B, 0x40}, 2); //初始化
        hal.ddi_prnt_esc(new byte[]{0x1D, 0x48,0x32}, 3); //
        hal.ddi_prnt_esc(new byte[]{0x1D, 0x77,0x06}, 3); //
        byte[] data = get1DCodeData(barCode);
        hal.ddi_prnt_esc(data, data.length); //
        hal.ddi_prnt_esc(new byte[]{0x1B, 0x4A, 68}, 3);
    }

    @Override
    public void addQrCode(Bundle format, String qrCode) throws RemoteException {
        hal.ddi_prnt_esc(new byte[]{0x1B, 0x40}, 2); //初始化
        String qrcodeStgring = getQrCodeCmd(qrCode);
        hal.ddi_prnt_esc(qrcodeStgring.getBytes(), qrcodeStgring.length());
        hal.ddi_prnt_esc(new byte[]{0x1B, 0x4A, 120}, 8);
    }

    @Override
    public void setSpace(int ySpace) throws RemoteException {

    }

    @Override
    public void startPrinter(AidlPrinterListener listener) throws RemoteException {
                hal.ddi_prnt_start(100);
    }

    @Override
    public int getStatus() throws RemoteException {
        return 0;
    }

    private byte[] byteMerger(byte[] a1, byte[]a2)
    {
        byte[] desb = new byte[a1.length+a2.length];
        System.arraycopy(a1, 0, desb, 0, a1.length);
        System.arraycopy(a2, 0, desb, a1.length, a2.length);
        return desb;
    }

    private byte[] get1DCodeData(String barcode)
    {
        byte[] cmd = new byte[]{0x1D, 0x6B,0x49};
        byte[] data;

        data = byteMerger(cmd, new byte[]{(byte)barcode.trim().length()});
        data = byteMerger(data, barcode.getBytes());

        return data;
    }

    public String getQrCodeCmd(String qrCode) {
        byte[] data;
        int store_len = qrCode.length() + 3;
        byte store_pL = (byte) (store_len % 256);
        byte store_pH = (byte) (store_len / 256);

        // QR Code: Select the model
        //              Hex     1D      28      6B      04      00      31      41      n1(x32)     n2(x00) - size of model
        // set n1 [49 x31, model 1] [50 x32, model 2] [51 x33, micro qr code]
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=140
        byte[] modelQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x04, (byte)0x00, (byte)0x31, (byte)0x41, (byte)0x32, (byte)0x00};

        // QR Code: Set the size of module
        // Hex      1D      28      6B      03      00      31      43      n
        // n depends on the printer
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=141
        byte[] sizeQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x43, (byte)0x08};

        //          Hex     1D      28      6B      03      00      31      45      n
        // Set n for error correction [48 x30 -> 7%] [49 x31-> 15%] [50 x32 -> 25%] [51 x33 -> 30%]
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=142
        byte[] errorQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x45, (byte)0x31};

        // QR Code: Store the data in the symbol storage area
        // Hex      1D      28      6B      pL      pH      31      50      30      d1...dk
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=143
        //                        1D          28          6B         pL          pH  cn(49->x31) fn(80->x50) m(48->x30) d1…dk
        byte[] storeQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, store_pL, store_pH, (byte)0x31, (byte)0x50, (byte)0x30};

        // QR Code: Print the symbol data in the symbol storage area
        // Hex      1D      28      6B      03      00      31      51      m
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=144
        byte[] printQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x51, (byte)0x30};

        data = byteMerger(modelQR, sizeQR);
        data = byteMerger(data, errorQR);
        data = byteMerger(data, storeQR);
        data = byteMerger(data, qrCode.getBytes());
        data = byteMerger(data, printQR);

        return new String(data);
    }


}
