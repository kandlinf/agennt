package de.ur.agennt.data.xgmml;

import de.ur.agennt.entity.xgmml.XgmmlEntity;

/**
 * Event class for XGMML reading and writing
 */
public class XgmmlEvent {
    private XgmmlEntity xgmmlEntity;
    private XgmmlEventType eventType;

    public XgmmlEvent(XgmmlEntity xgmmlEntity, XgmmlEventType eventType) {
        this.xgmmlEntity = xgmmlEntity;
        this.eventType = eventType;
    }

    public XgmmlEntity getXgmmlEntity() {
        return xgmmlEntity;
    }

    public XgmmlEventType getEventType() {
        return eventType;
    }
}
