package me.ooi.testagent.requesthandler;

import me.ooi.httpserver.nio.SelectorMananger;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class SingleCmdSelectorMananger extends SelectorMananger{
	
	public static final SingleCmdSelectorMananger INSTANCE = new SingleCmdSelectorMananger() ; 
	
}
