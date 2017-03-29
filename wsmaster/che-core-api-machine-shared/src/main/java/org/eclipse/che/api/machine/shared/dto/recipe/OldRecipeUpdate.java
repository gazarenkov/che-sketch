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
package org.eclipse.che.api.machine.shared.dto.recipe;

import org.eclipse.che.api.machine.shared.ManagedOldRecipe;
import org.eclipse.che.dto.shared.DTO;

import java.util.List;

/**
 * Describes recipe update.
 *
 * @author Eugene Voevodin
 */
@DTO
public interface OldRecipeUpdate extends ManagedOldRecipe {

    void setId(String id);

    OldRecipeUpdate withId(String id);

    void setName(String name);

    OldRecipeUpdate withName(String name);

    void setType(String type);

    OldRecipeUpdate withType(String type);

    void setScript(String script);

    OldRecipeUpdate withScript(String script);

    void setTags(List<String> tags);

    OldRecipeUpdate withTags(List<String> tags);

    void setDescription(String description);

    OldRecipeUpdate withDescription(String description);
}
