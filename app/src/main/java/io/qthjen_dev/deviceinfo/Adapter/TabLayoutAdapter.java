package io.qthjen_dev.deviceinfo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.qthjen_dev.deviceinfo.Fragment.AboutFragment;
import io.qthjen_dev.deviceinfo.Fragment.BetteryInfoFragment;
import io.qthjen_dev.deviceinfo.Fragment.CpuInfoFragment;
import io.qthjen_dev.deviceinfo.Fragment.DeviceFragment;
import io.qthjen_dev.deviceinfo.Fragment.MemoryInfoFragment;
import io.qthjen_dev.deviceinfo.Fragment.SensorInfoFragment;
import io.qthjen_dev.deviceinfo.Fragment.SystemInfoFragment;

/**
 * Created by q-thjen on 2/2/18.
 */

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private String system, cpu, device, bettery, memory, sensor, about;

    public TabLayoutAdapter(FragmentManager fm, String system, String cpu, String device,
                            String memory, String bettery, String sensor, String about) {
        super(fm);

        this.system = system;
        this.cpu = cpu;
        this.device = device;
        this.bettery = bettery;
        this.memory = memory;
        this.sensor = sensor;
        this.about = about;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                SystemInfoFragment systemInfoFragment = new SystemInfoFragment();
                return systemInfoFragment;

            case 1:
                CpuInfoFragment cpuInfoFragment = new CpuInfoFragment();
                return cpuInfoFragment;

            case 2:
                DeviceFragment deviceFragment = new DeviceFragment();
                return deviceFragment;

            case 3:
                MemoryInfoFragment memoryInfoFragment = new MemoryInfoFragment();
                return memoryInfoFragment;

            case 4:
                BetteryInfoFragment betteryInfoFragment = new BetteryInfoFragment();
                return betteryInfoFragment;

            case 5:
                SensorInfoFragment sensorInfoFragment = new SensorInfoFragment();
                return sensorInfoFragment;

            case 6:
                AboutFragment aboutFragment = new AboutFragment();
                return aboutFragment;

            default:return null;

        }

    }

    @Override
    public int getCount() {
        return 7;
    }

    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0: return system;
            case 1: return cpu;
            case 2: return device;
            case 3: return memory;
            case 4: return bettery;
            case 5: return sensor;
            case 6: return about;

            default: return null;

        }

    }

}
