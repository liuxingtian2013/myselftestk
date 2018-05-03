package com.newland.service;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by Administrator on 2018\3\23 0023.
 */

public class AidlDeviceInfo extends com.newland.aidl.deviceInfo.AidlDeviceInfo.Stub {
    /**
     * 获取终端硬件序列号
     * @return
     * @throws RemoteException
     */
    @Override
    public String getTUSN() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getTUSN");
        return "1111";
    }

    /**
     * 获取设备序列号
     * @return
     * @throws RemoteException
     */
    @Override
    public String getSN() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getSN");
        return "000101000201";
    }

    /**
     * 获取客户定义的设备序列号
     * @return
     * @throws RemoteException
     */
    @Override
    public String getCSN() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getCSN");
        return "1111";
    }

    /**
     * 获得厂商id
     * @return
     * @throws RemoteException
     */
    @Override
    public String getVID() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getVID");
        return "1111";
    }

    /**
     * 获得厂商name
     * @return
     * @throws RemoteException
     */
    @Override
    public String getVName() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getVName");
        return "ytkj";
    }

    /**
     * 获取秘钥序列号
     * @return
     * @throws RemoteException
     */
    @Override
    public String getKSN() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getKSN");
        return "1111";
    }

    /**
     * 获得android操作系统版本
     * @return
     * @throws RemoteException
     */
    @Override
    public String getAndroidOSVersion() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getAndroidOSVersion");
        return "1111";
    }

    /**
     * 获取android内核版本
     * @return
     * @throws RemoteException
     */
    @Override
    public String getAndroidKernelVersion() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getAndroidKernelVersion");
        return "1111";
    }

    /**
     * 获取终端固件版本
     * @return
     * @throws RemoteException
     */
    @Override
    public String getFirmwareVersion() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getFirmwareVersion");
        return "ytkj";
    }

    /**
     * 获取终端型号
     * @return
     * @throws RemoteException
     */
    @Override
    public String getDeviceModel() throws RemoteException {
        System.out.println("---------------AidlDeviceInfo--------------------getDeviceModel");
        return "S500";
    }

    @Override
    public IBinder asBinder() {
        System.out.println("---------------AidlDeviceInfo--------------------asBinder");
        return null;
    }
}
