package me.ooi.httpserver.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import me.ooi.utils.XLog;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class SelectorMananger {

	private static final String TAG = "SelectorMananger" ; 

	protected boolean running = false ; 
	
	protected Selector selector ; 
	
	private Queue<Runnable> tasks = new LinkedList<Runnable>() ; 
	
	public void stop() {
		running = false ; 
		try {
			if( selector != null ){
				selector.close();
			}
		} catch (IOException e) {
			XLog.error(TAG, e.getMessage(), e);
		}
	}
	
	public Selector open() throws IOException{
		if( selector != null ){
			throw new IllegalStateException("selector is opened!") ; 
		}
		
		selector = Selector.open(); 
		return selector ; 
	}
	
	public Selector getSelector(){
		return selector ; 
	}
	
	public void insertTask(Runnable task){
		tasks.offer(task) ; 
		if( selector != null ){
			selector.wakeup() ; 
		}
	}
	
	public void start(){
		if( running ){
			throw new IllegalStateException("server is running!") ; 
		}
		running = true ; 
		
        try {
			while( running ){
				select(); 
				
				Runnable task = null ;
				while( (task = tasks.poll()) != null ){
					task.run();
				}
			}
		} finally {
			stop() ; 
		}
	}
	
	protected void select(){
		if( selector == null ){
			return ; 
		}
		
		try {
			selector.select(1000*2);
            Set<SelectionKey> selected = selector.selectedKeys();
            Iterator<SelectionKey> it = selected.iterator();
            while (it.hasNext()) {
                dispatch((SelectionKey) (it.next()));
            }
            selected.clear();
		} catch (Exception e) {
			XLog.error(TAG, e.getMessage(), e);
		}
	}
	
	private void dispatch(SelectionKey k) {
		if( !k.isValid() ){
			XLog.info(TAG, "SelectionKey is valid");
			return ; 
		}
		
		//所有分发都在这里
		try {
			NIOExecutor r = (NIOExecutor) (k.attachment());
	        if (r != null) {
	            r.execute();
	        }
		} catch (Exception e) {
			XLog.error(TAG, e.getMessage(), e);
		}
    }

}
