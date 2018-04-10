package io.pivotal.pal.tracker.repository;

import io.pivotal.pal.tracker.model.TimeEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    HashMap<Long, TimeEntry> inMemoryMap;

    public InMemoryTimeEntryRepository(){
        inMemoryMap = new HashMap<Long, TimeEntry>();
    }

    public TimeEntry create(TimeEntry timeEntry) {
        if(timeEntry.getId() == 0){
            timeEntry.setId(inMemoryMap.size()+1);
        }
        inMemoryMap.put(timeEntry.getId(), timeEntry);
        return find(timeEntry.getId());
    }

    public TimeEntry find(long id) {
        return inMemoryMap.get(id);
    }

    public List<TimeEntry> list() {
        List<TimeEntry> list = new ArrayList<TimeEntry>(inMemoryMap.values());
        return list;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        inMemoryMap.put(id, timeEntry);
        return find(id);
    }

    public TimeEntry delete(long id) {
        return inMemoryMap.remove(id);
    }
}
