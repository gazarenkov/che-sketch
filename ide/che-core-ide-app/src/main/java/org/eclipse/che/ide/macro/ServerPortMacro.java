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
package org.eclipse.che.ide.macro;

import com.google.common.annotations.Beta;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.api.core.model.machine.OldServer;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.machine.DevMachine;
import org.eclipse.che.ide.api.machine.MachineServer;
import org.eclipse.che.ide.api.macro.Macro;
import org.eclipse.che.ide.api.macro.MacroRegistry;
import org.eclipse.che.ide.rest.UrlBuilder;

import java.util.Map;
import java.util.Set;

/**
 * Provider which is responsible for the retrieving the port of the registered server.
 * <p>
 * Macro provided: <code>${server.[port].port}</code>
 *
 * @author Vlad Zhukovskyi
 * @see AbstractServerMacro
 * @see DevMachine
 * @see OldServer#getAddress()
 * @since 4.7.0
 */
@Beta
@Singleton
public class ServerPortMacro extends AbstractServerMacro {

    public static final String KEY = "${server.%.port}";

    @Inject
    public ServerPortMacro(MacroRegistry providerRegistry,
                           EventBus eventBus,
                           AppContext appContext) {
        super(providerRegistry, eventBus, appContext);
    }

    /** {@inheritDoc} */
    @Override
    public Set<Macro> getMacros(DevMachine devMachine) {
        final Set<Macro> providers = Sets.newHashSet();

        for (Map.Entry<String, ? extends MachineServer> entry : devMachine.getServers().entrySet()) {

            UrlBuilder urlBuilder = new UrlBuilder(entry.getValue().getUrl());

            if(!urlBuilder.containsPort())
                continue;


//            if (!entry.getValue().getAddress().contains(":")) {
//                continue;
//            }

            final String externalPort = urlBuilder.getPort();

            Macro macro = new CustomMacro(KEY.replace("%", entry.getKey()),
                                          externalPort,
                                          "Returns port of a server registered by name");

            providers.add(macro);

//            // register port without "/tcp" suffix
//            if (entry.getKey().endsWith("/tcp")) {
//                final String port = entry.getKey().substring(0, entry.getKey().length() - 4);
//
//                Macro shortMacro = new CustomMacro(KEY.replace("%", port),
//                                                   externalPort,
//                                                   "Returns port of a server registered by name");
//
//                providers.add(shortMacro);
//            }
        }

        return providers;
    }
}
