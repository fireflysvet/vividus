/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.vividus.ui.web.action.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

class ActionAttributeTypeTests
{
    @Test
    void getSearchTypes()
    {
        Set<WebActionAttributeType> searchTypes = Set.of(
                WebActionAttributeType.LINK_TEXT,
                WebActionAttributeType.LINK_URL,
                WebActionAttributeType.LINK_URL_PART,
                WebActionAttributeType.CASE_SENSITIVE_TEXT,
                WebActionAttributeType.CASE_INSENSITIVE_TEXT,
                WebActionAttributeType.TOOLTIP,
                WebActionAttributeType.XPATH,
                WebActionAttributeType.CSS_SELECTOR,
                WebActionAttributeType.TAG_NAME,
                WebActionAttributeType.IMAGE_SRC,
                WebActionAttributeType.IMAGE_SRC_PART,
                WebActionAttributeType.BUTTON_NAME,
                WebActionAttributeType.FIELD_NAME,
                WebActionAttributeType.CHECKBOX_NAME,
                WebActionAttributeType.ELEMENT_NAME,
                WebActionAttributeType.ID,
                WebActionAttributeType.CLASS_NAME);
        assertEquals(searchTypes, WebActionAttributeType.getSearchTypes());
    }

    @Test
    void getFilterTypes()
    {
        Set<WebActionAttributeType> filterTypes = Set.of(
                WebActionAttributeType.LINK_URL,
                WebActionAttributeType.LINK_URL_PART,
                WebActionAttributeType.CASE_SENSITIVE_TEXT,
                WebActionAttributeType.TOOLTIP,
                WebActionAttributeType.IMAGE_SRC_PART,
                WebActionAttributeType.TEXT_PART,
                WebActionAttributeType.PLACEHOLDER,
                WebActionAttributeType.STATE,
                WebActionAttributeType.DROP_DOWN_STATE,
                WebActionAttributeType.VALIDATION_ICON_SOURCE,
                WebActionAttributeType.RELATIVE_TO_PARENT_WIDTH,
                WebActionAttributeType.CLASS_ATTRIBUTE_PART,
                WebActionAttributeType.FIELD_TEXT,
                WebActionAttributeType.FIELD_TEXT_PART,
                WebActionAttributeType.DROP_DOWN_TEXT);
        assertEquals(filterTypes, WebActionAttributeType.getFilterTypes());
    }
}
