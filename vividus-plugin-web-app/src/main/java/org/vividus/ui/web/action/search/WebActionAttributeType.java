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

import org.vividus.ui.action.search.IActionAttributeKey;

public enum WebActionAttributeType implements IActionAttributeKey
{
    LINK_TEXT,
    LINK_URL,
    LINK_URL_PART,
    CASE_SENSITIVE_TEXT,
    CASE_INSENSITIVE_TEXT,
    TOOLTIP,
    CSS_SELECTOR,
    TAG_NAME,
    IMAGE_SRC,
    IMAGE_SRC_PART,
    BUTTON_NAME,
    FIELD_NAME,
    TEXT_PART,
    PLACEHOLDER,
    STATE,
    DROP_DOWN_STATE,
    VALIDATION_ICON_SOURCE,
    RELATIVE_TO_PARENT_WIDTH,
    CLASS_ATTRIBUTE_PART,
    CHECKBOX_NAME,
    FIELD_TEXT,
    FIELD_TEXT_PART,
    DROP_DOWN_TEXT,
    ELEMENT_NAME;

    @Override
    public String getKey()
    {
        return this.name();
    }
}
