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
package org.eclipse.che.api.machine.server.jpa;

import com.google.inject.TypeLiteral;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.eclipse.che.account.spi.AccountImpl;
import org.eclipse.che.api.core.model.workspace.Workspace;
import org.eclipse.che.api.machine.server.model.impl.SnapshotImpl;
import org.eclipse.che.api.machine.server.recipe.OldRecipeImpl;
import org.eclipse.che.api.machine.server.spi.RecipeDao;
import org.eclipse.che.api.machine.server.spi.SnapshotDao;
import org.eclipse.che.commons.test.db.H2JpaCleaner;
import org.eclipse.che.commons.test.db.H2TestHelper;
import org.eclipse.che.commons.test.tck.TckModule;
import org.eclipse.che.commons.test.tck.TckResourcesCleaner;
import org.eclipse.che.commons.test.tck.repository.JpaTckRepository;
import org.eclipse.che.commons.test.tck.repository.TckRepository;
import org.eclipse.che.commons.test.tck.repository.TckRepositoryException;
import org.eclipse.che.core.db.DBInitializer;
import org.eclipse.che.core.db.schema.SchemaInitializer;
import org.eclipse.che.core.db.schema.impl.flyway.FlywaySchemaInitializer;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Anton Korneta
 */
public class JpaTckModule extends TckModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("main"));
        bind(DBInitializer.class).asEagerSingleton();
        bind(SchemaInitializer.class).toInstance(new FlywaySchemaInitializer(H2TestHelper.inMemoryDefault(), "che-schema"));
        bind(TckResourcesCleaner.class).to(H2JpaCleaner.class);

        bind(new TypeLiteral<TckRepository<OldRecipeImpl>>() {}).toInstance(new JpaTckRepository<>(OldRecipeImpl.class));
        bind(new TypeLiteral<TckRepository<SnapshotImpl>>() {}).toInstance(new JpaTckRepository<>(SnapshotImpl.class));
        bind(new TypeLiteral<TckRepository<Workspace>>() {}).toInstance(new TestWorkspacesTckRepository());
        bind(new TypeLiteral<TckRepository<AccountImpl>>() {}).toInstance(new JpaTckRepository<>(AccountImpl.class));

        bind(RecipeDao.class).to(JpaRecipeDao.class);
        bind(SnapshotDao.class).to(JpaSnapshotDao.class);
    }

    private static class TestWorkspacesTckRepository extends JpaTckRepository<Workspace> {

        public TestWorkspacesTckRepository() { super(TestWorkspaceEntity.class); }

        @Override
        public void createAll(Collection<? extends Workspace> entities) throws TckRepositoryException {
            super.createAll(entities.stream()
                                    .map(TestWorkspaceEntity::new)
                                    .collect(Collectors.toList()));
        }
    }
}
