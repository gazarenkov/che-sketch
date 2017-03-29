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
package org.eclipse.che.api.machine.server.recipe.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.eclipse.che.api.core.model.machine.OldRecipe;
import org.eclipse.che.api.machine.server.recipe.OldRecipeImpl;

import java.lang.reflect.Type;

/**
 * Type adapter for {@link OldRecipe recipe}.
 *
 * @author Eugene Voevodin
 */
public class RecipeTypeAdapter implements JsonDeserializer<OldRecipe>, JsonSerializer<OldRecipe> {

    @Override
    public OldRecipe deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        final OldRecipeImpl recipe = new OldRecipeImpl();
        final JsonObject recipeObj = element.getAsJsonObject();
        recipe.setType(recipeObj.get("type") == null ? null : recipeObj.get("type").getAsString());
        recipe.setScript(recipeObj.get("script") == null ? null : recipeObj.get("script").getAsString());
        return recipe;
    }

    @Override
    public JsonElement serialize(OldRecipe recipe, Type type, JsonSerializationContext context) {
        return context.serialize(recipe, OldRecipeImpl.class);
    }
}
