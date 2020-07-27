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

package org.vividus.ui.web.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vividus.ui.action.search.SearchAttributes;
import org.vividus.ui.action.search.SearchParameters;
import org.vividus.ui.action.search.Visibility;
import org.vividus.ui.web.action.search.WebActionAttributeType;

class SearchAttributesConversionUtilsTests
{
    private static final String ENABLED = "enabled";
    private static final String ID = "id";
    private static final String INVALID_LOCATOR_MESSAGE = "Invalid locator format. "
            + "Expected matches [(?:By\\.)?([a-zA-Z]+)\\((.+?)\\):?([a-zA-Z]*)?] Actual: [";
    private static final char CLOSING_BRACKET = ']';
    private static final String INVALID_LOCATOR = "To.xpath(.a)";
    private static final String TEXT = "text";

    @ParameterizedTest
    @MethodSource("actionAttributeSource")
    void testConvertToSearchAttributes(SearchAttributes expected, String testValue)
    {
        assertEquals(expected, SearchAttributesConversionUtils.convertToSearchAttributes(testValue));
    }

    @Test
    void testConvertToSearchAttributesInvalidLocatorType()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> SearchAttributesConversionUtils.convertToSearchAttributes("By.jquery(.a)"));
        assertEquals("Unsupported locator type: jquery", exception.getMessage());
    }

    @Test
    void testConvertToSearchAttributesInvalidLocatorFormat()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> SearchAttributesConversionUtils.convertToSearchAttributes(INVALID_LOCATOR));
        assertEquals(INVALID_LOCATOR_MESSAGE + INVALID_LOCATOR + CLOSING_BRACKET, exception.getMessage());
    }

    @Test
    void testConvertToSearchAttributesEmptyStringLocator()
    {
        assertThrows(IllegalArgumentException.class,
            () -> SearchAttributesConversionUtils.convertToSearchAttributes(StringUtils.EMPTY),
            INVALID_LOCATOR_MESSAGE + CLOSING_BRACKET);
    }

    @Test
    void testConvertToSearchAttributesSet()
    {
        SearchAttributes searchAttributesId = new SearchAttributes(WebActionAttributeType.CSS_SELECTOR, "#main");
        SearchAttributes searchAttributesClassName = new SearchAttributes(WebActionAttributeType.CLASS_NAME, "class");
        assertEquals(new HashSet<>(Arrays.asList(searchAttributesId, searchAttributesClassName)),
                SearchAttributesConversionUtils.convertToSearchAttributesSet("By.cssSelector(#main),"
                        + " By.className(class)"));
    }

    static Stream<Arguments> actionAttributeSource()
    {
        return Stream.of(
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE), "By.id(id):i"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE), "id(id):i"),
                Arguments.of(createAttributes(WebActionAttributeType.CSS_SELECTOR, "#id", Visibility.ALL),
                        "By.CSSselector(#id):all"),
                Arguments.of(createAttributes(WebActionAttributeType.CSS_SELECTOR, "#id1", Visibility.VISIBLE),
                        "cssSelector(#id1)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.CLASS_NAME, "clazz"), "By.className(clazz)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.LINK_TEXT, "url"), "By.linkText(url)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.ELEMENT_NAME, "button"), "By.name(button)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.XPATH, "//a"), "By.xpath(//a)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.XPATH,
                        "//a[contains(normalize-space(@class),'cl-s')]|//b[contains(normalize-space(@class),'cl-s')]"),
                        "By.xpath(//a[contains(@class,'cl-s')]|//b[contains(@class,'cl-s')]):v"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.TAG_NAME, "h1"), "By.tagName(h1)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.TAG_NAME, "a")
                        .addFilter(WebActionAttributeType.TEXT_PART, TEXT), "By.partiallinktext(text)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.LINK_URL, "https://home.page"),
                        "By.linkUrl(https://home.page)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.LINK_URL_PART, "part"), "By.linkUrlPart(part)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.FIELD_NAME, "name"), "By.fieldName(name)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.CASE_SENSITIVE_TEXT, TEXT),
                        "By.caseSensitiveText(text)"),
                Arguments.of(new SearchAttributes(WebActionAttributeType.CASE_INSENSITIVE_TEXT, "teXt"),
                        "By.caseInsensitiveText(teXt)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                       WebActionAttributeType.TEXT_PART, TEXT), "By.id(id):i->filter.textPart(text)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.ALL,
                        WebActionAttributeType.PLACEHOLDER, TEXT), "By.id(id):all->filter.placeholder(text)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.DROP_DOWN_STATE, ENABLED), "By.id(id):i->filter.dropDownState(enabled)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.DROP_DOWN_TEXT, TEXT), "By.id(id):i->filter.dropDownText(text)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.VALIDATION_ICON_SOURCE, "/src"),
                        "By.id(id):i->filter.validationICONsource(/src)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.RELATIVE_TO_PARENT_WIDTH, "5"),
                        "By.id(id):i->filter.relativeToParentWidth(5)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.CLASS_ATTRIBUTE_PART, TEXT),
                        "By.id(id):i->filter.classattributepart(text)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.FIELD_TEXT, TEXT), "By.id(id):i->filter.fieldText(text)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.FIELD_TEXT_PART, TEXT), "By.id(id):i->filter.fieldtextpart(text)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.STATE, ENABLED), "By.id(id):i->filter.state(enabled)"),
                Arguments.of(createAttributes(WebActionAttributeType.ID, ID, Visibility.INVISIBLE,
                        WebActionAttributeType.STATE, ENABLED).addFilter(WebActionAttributeType.TEXT_PART, TEXT)
                        .addFilter(WebActionAttributeType.CLASS_ATTRIBUTE_PART, "clas"),
                        "By.id(id):i->filter.state(enabled).textPart(text).classattributepart(clas).notFilter(any)"));
    }

    private static SearchAttributes createAttributes(WebActionAttributeType type, String value, Visibility elementType)
    {
        SearchParameters searchParameters = new SearchParameters(value, elementType);
        return new SearchAttributes(type, searchParameters);
    }

    private static SearchAttributes createAttributes(WebActionAttributeType type, String value, Visibility elementType,
            WebActionAttributeType filter, String filterValue)
    {
        return createAttributes(type, value, elementType).addFilter(filter, filterValue);
    }
}
