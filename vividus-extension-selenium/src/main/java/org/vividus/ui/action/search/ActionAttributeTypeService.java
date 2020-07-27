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

package org.vividus.ui.action.search;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Named;

@Named
public class ActionAttributeTypeService implements IActionAttributeTypeService
{
    private final Set<IActionAttributeType> attributeTypes;

    private final Set<IActionAttributeType> searchTypes;
    private final Set<IActionAttributeType> filterTypes;

    public ActionAttributeTypeService(Set<IActionAttributeType> attributeTypes)
    {
        this.attributeTypes = attributeTypes;

        this.searchTypes = filterByActionClass(IElementSearchAction.class, t -> Objects.nonNull(t.getSearchLocatorBuilder()));
        this.filterTypes = filterByActionClass(IElementFilterAction.class, t -> false);
    }

    @Override
    public IActionAttributeType findAttributeType(IActionAttributeKey attributeKey)
    {
        return attributeTypes.stream()
                             .filter(att -> att.getAttributeKey() == attributeKey)
                             .findFirst()
                             .orElseThrow(() -> new IllegalArgumentException("Unknown attribute type: "
                                 + attributeKey.getKey()));
    }

    @Override
    public Set<IActionAttributeType> getSearchTypes()
    {
        return searchTypes;
    }

    @Override
    public Set<IActionAttributeType> getFilterTypes()
    {
        return filterTypes;
    }

    private Set<IActionAttributeType> filterByActionClass(Class<? extends IElementAction> actionClass,
            Predicate<IActionAttributeType> orPredicate)
    {
        return attributeTypes.stream()
                             .filter(t -> t.getActionClass() != null &&
                                 actionClass.isAssignableFrom(t.getActionClass()) || orPredicate.test(t))
                             .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }
}
