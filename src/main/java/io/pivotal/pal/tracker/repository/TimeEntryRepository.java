package io.pivotal.pal.tracker.repository;

import io.pivotal.pal.tracker.model.TimeEntry;
import java.util.List;

public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry timeEntry);

    public TimeEntry find(long id);

    public List<TimeEntry> list();

    public TimeEntry update(long id, TimeEntry timeEntry);

    public TimeEntry delete(long id);

}
