package com.dodotdo.himsadmin.model;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Omjoon on 16. 4. 22..
 */
public class Rooms {
    static Rooms instance;

    public interface OnRoomsChangeListener {
        void onChange();
    }

    OnRoomsChangeListener listener;

    public static Rooms getInstance() {
        if (instance == null) {
            instance = new Rooms();
        }

        return instance;
    }

    List<Room> list;
    HashMap<String, Room> maps;
    //floor,count;
    HashMap<String, Floor> floorMap;

    public Rooms() {
        list = new ArrayList<>();
        maps = new HashMap<>();
        floorMap = new HashMap<>();
    }

    public void setData(List<Room> data) {
        clear();
        for (Room item : data) {
            String floor = item.getFloor();
            if (floorMap.containsKey(floor)) {
                floorMap.get(floor).count++;
            } else {
                floorMap.put(floor, new Floor(floor, 1));
            }
            list.add(item);
            maps.put(item.getRoomNumber(), item);
        }
    }

    public List<Room> getList(String floor) {
        if(floor == null){
            return this.list;
        }
        List<Room> list = new ArrayList<>();
        for (Room room : this.list) {
            if (room.getFloor().equals(floor)) {
                list.add(room);
            }
        }
        return list;
    }

    public void clear() {
        list.clear();
        maps.clear();
        floorMap.clear();
    }

    public List<Floor> getFloors() {
        List<Floor> floors = new ArrayList<>(floorMap.values());
        Collections.sort(floors, myComparator);
        return floors;
    }

    public Floor getRoomCountsFromFloor(String floor) {
        return floorMap.get(floor);
    }

    public void addItem(Room item) {
        if (maps.containsKey(item.roomNumber)) {
            Room before = maps.get(item.roomNumber);
            maps.remove(before);
            int position = list.indexOf(before);
            list.remove(before);
            maps.put(item.getRoomNumber(), item);
            list.add(position, item);
        } else {
            maps.put(item.getRoomNumber(), item);
            list.add(item);
        }
        notifyChangeData();
    }

    private void notifyChangeData() {
        if (listener != null) {
            listener.onChange();
        }
    }

    public List<Room> getList() {
        return list;
    }

    public void changeCleanInRoom(Clean clean) {
        maps.get(clean.getRoomNumber()).clean.add(0, clean);
    }


    private final static Comparator myComparator = new Comparator<Floor>() {
        private final Collator collator = Collator.getInstance();

        @Override
        public int compare(Floor lhs, Floor rhs) {

            return Integer.valueOf(lhs.floor) > Integer.valueOf(rhs.floor) ? 1 : -1;
        }
    };

    public void setListener(OnRoomsChangeListener listener) {
        this.listener = listener;
    }
}
