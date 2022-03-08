package com.sainnt.cqrs.core.domain;

import com.sainnt.cqrs.core.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public abstract class AggregateRoot {
    protected String id;
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();

    public String getId() {
        return id;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent){
        try{
            Method apply = event.getClass().getDeclaredMethod("apply", event.getClass());
            apply.setAccessible(true);
            apply.invoke(this, event);
        }catch (NoSuchMethodException e){
            log.warn("Applying method is not found in aggregate for {}",event.getClass().getName());
        }
        catch (Exception e){
            log.error("Error applying event to aggregate",e);
        }
        finally {
            if(isNewEvent)
                changes.add(event);
        }
    }
    public void raiseEvent(BaseEvent event){
        applyChange(event,true);
    }

    public void replayEvents(Iterable<BaseEvent> events){
        events.forEach(event -> applyChange(event,false));
    }

    public void markChangesCommitted(){
        changes.clear();
    }
}
