/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.api.workspace.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event will be fired then server send output from some machine
 *
 * @author Vitalii Parfonov
 */
public class EnvironmentOutputEvent extends GwtEvent<EnvironmentOutputEvent.Handler> {


    public interface Handler extends EventHandler {
        /**
         * Perform actions when receive Environment Output message
         *
         * @param event
         */
        void onEnvironmentOutputEvent(EnvironmentOutputEvent event);
    }


    public static final Type<EnvironmentOutputEvent.Handler> TYPE = new Type<>();


    /**
     * Content of log message
     */
    private  final String content;

    /**
     * OldMachine name
     */
    private final String machineName;

    public EnvironmentOutputEvent(String content, String machineName) {
        this.content = content;
        this.machineName = machineName;
    }


    public String getContent() {
        return content;
    }

    public String getMachineName() {
        return machineName;
    }


    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onEnvironmentOutputEvent(this);
    }
}
