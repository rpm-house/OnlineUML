package org.infosys.bo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;


public class Transformer<T> {

    private static final XStream XSTREAM_INSTANCE = null;

    public T getObjectFromJSON(String json){
        return (T) getInstance().fromXML(json);
    }

    public String getJSONFromObject(T t){
        return getInstance().toXML(t);
    }

    private XStream getInstance(){
        if(XSTREAM_INSTANCE==null){
            return new XStream(new JettisonMappedXmlDriver());
        }  else {
            return XSTREAM_INSTANCE;
        }
    }

    public T getObjectFromXML(String xml){
        return (T)getStaxDriverInstance().fromXML(xml);
    }

    public String getXMLFromObject(T t){
        return getStaxDriverInstance().toXML(t);
    }

    public T getObjectFromXMLUsingDomDriver(String xml){
        return (T)getDomDriverInstance().fromXML(xml);
    }

    public String getXMLFromObjectUsingDomDriver(T t){
        return getDomDriverInstance().toXML(t);
    }

    private XStream getStaxDriverInstance(){
        if(XSTREAM_INSTANCE==null) {
            return new XStream(new StaxDriver());
        }else{
            return XSTREAM_INSTANCE;
        }
    }

    private XStream getDomDriverInstance(){
        if(XSTREAM_INSTANCE==null){
            return new XStream(new DomDriver());
        }else{
            return XSTREAM_INSTANCE;
        }
    }
}