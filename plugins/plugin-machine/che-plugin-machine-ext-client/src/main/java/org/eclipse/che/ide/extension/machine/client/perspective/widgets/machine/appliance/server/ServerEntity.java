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
package org.eclipse.che.ide.extension.machine.client.perspective.widgets.machine.appliance.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import org.eclipse.che.api.core.model.workspace.runtime.ServerRuntime;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The class which describes entity which store information of current server.
 *
 * @author Dmitry Shnurenko
 */
public class ServerEntity  {

    private final ServerRuntime descriptor;
    private final String    ref;

    @Inject
    public ServerEntity(@Assisted String ref, @Assisted ServerRuntime descriptor) {
        this.ref = ref;
        this.descriptor = descriptor;
    }

    @NotNull
    public String getRef() {
        return ref;
    }

//    @NotNull
//    @Override
//    public String getAddress() {
//        return descriptor.getAddress();
//    }
//
//    @Override
//    public String getProtocol() {
//        return descriptor.getProtocol();
//    }

//    @Override
    public String getUrl() {
        return descriptor.getUrl();
    }

//    @NotNull
//    @Override
//    public String getRef() {
//        return descriptor.getRef();
//    }
//
//    @Override
//    public ServerProperties getProperties() {
//        return new ServerPropertiesImpl(descriptor.getProperties());
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerEntity)) return false;
        ServerEntity other = (ServerEntity)o;
        return Objects.equals(descriptor, other.descriptor) &&
               Objects.equals(ref, other.ref);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + Objects.hashCode(descriptor);
        hash = hash * 31 + Objects.hashCode(ref);
        return hash;
    }

    @Override
    public String toString() {
        return "Server{" +
               "descriptor=" + descriptor +
               ", ref='" + ref + '\'' +
               '}';
    }
}
