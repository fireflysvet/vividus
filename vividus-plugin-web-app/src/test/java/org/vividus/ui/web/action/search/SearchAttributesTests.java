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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.vividus.ui.action.search.SearchAttributes;

class SearchAttributesTests
{
    private static final String TEST_STATE = "testState";
    private static final String TEST_NAME = "testName";
    private static final String TEST_TEXT = "testText";
    private static final String TEST_IMAGE_SRC = "testImageSrc";

    private SearchAttributes searchAttributes;

    @BeforeEach
    void beforeEach()
    {
        searchAttributes = new SearchAttributes(WebActionAttributeType.XPATH, "testXpath");
    }

    @Test
    void testAddCompetingFilter()
    {
        searchAttributes.addFilter(WebActionAttributeType.CASE_SENSITIVE_TEXT, TEST_TEXT);
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
            () -> searchAttributes.addFilter(WebActionAttributeType.TEXT_PART, TEST_TEXT));
        assertEquals("Competing attributes: 'Text part' and 'Case sensitive text'", exception.getMessage());
    }

    @Test
    void testAddCompetingSearchAttribute()
    {
        searchAttributes = new SearchAttributes(WebActionAttributeType.IMAGE_SRC, TEST_IMAGE_SRC);
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
            () -> searchAttributes.addFilter(WebActionAttributeType.IMAGE_SRC_PART, TEST_IMAGE_SRC));
        assertEquals("Competing attributes: 'Image source part' and 'Image source'", exception.getMessage());
    }

    @Test
    void testAddFilter()
    {
        searchAttributes.addFilter(WebActionAttributeType.CASE_SENSITIVE_TEXT, TEST_NAME);
        assertEquals(1, searchAttributes.getFilterAttributes().size());
    }

    @Test
    void testAddFiltersNoCompetence()
    {
        searchAttributes.addFilter(WebActionAttributeType.PLACEHOLDER, TEST_TEXT);
        searchAttributes.addFilter(WebActionAttributeType.STATE, TEST_STATE);
        assertEquals(2, searchAttributes.getFilterAttributes().size());
    }

    @Test
    void testAddTwoFilters()
    {
        searchAttributes.addFilter(WebActionAttributeType.TEXT_PART, TEST_TEXT);
        searchAttributes.addFilter(WebActionAttributeType.STATE, TEST_STATE);
        assertEquals(2, searchAttributes.getFilterAttributes().size());
    }

    @Test
    void testAddFilterNotApplicable()
    {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
            () -> searchAttributes.addFilter(WebActionAttributeType.BUTTON_NAME, TEST_NAME));
        assertEquals("Filter by attribute 'BUTTON_NAME' is not supported", exception.getMessage());
    }

    @Test
    void testAddOneFilterWithMultipleValues()
    {
        searchAttributes.addFilter(WebActionAttributeType.CASE_SENSITIVE_TEXT, TEST_TEXT);
        searchAttributes.addFilter(WebActionAttributeType.CASE_SENSITIVE_TEXT, TEST_TEXT);
        Map<IActionAttributeType, List<String>> filterAttributes = searchAttributes.getFilterAttributes();
        assertEquals(1, filterAttributes.size());
        assertEquals(TEST_TEXT, filterAttributes.get(WebActionAttributeType.CASE_SENSITIVE_TEXT).get(0));
        assertEquals(TEST_TEXT, filterAttributes.get(WebActionAttributeType.CASE_SENSITIVE_TEXT).get(1));
    }
}
