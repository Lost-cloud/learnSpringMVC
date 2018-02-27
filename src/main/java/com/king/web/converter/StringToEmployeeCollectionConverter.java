package com.king.web.converter;

import com.king.domain.Employee;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.CollectionFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.*;

public class StringToEmployeeCollectionConverter implements ConditionalGenericConverter {

    private final ConversionService conversionService;

    public StringToEmployeeCollectionConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        LogManager.getLogger().info("进入EmployeeCollectionConverter matcher");
        return targetType.getElementTypeDescriptor()==null||this.conversionService.canConvert(sourceType, targetType.getElementTypeDescriptor());
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Collection.class));
    }

    @Override
    public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        LogManager.getLogger().info("进入EmployeeCollectionConverter convert");
        if(source==null) return null;
        String string = (String) source;
        String[] fields = StringUtils.commaDelimitedListToStringArray(string);
        TypeDescriptor elementDesc = targetType.getElementTypeDescriptor();
        Collection<Object> target = CollectionFactory.createCollection(targetType.getType(),elementDesc != null ? elementDesc.getType() : null, fields.length);
        for (String field : fields) {
            Object targetElement =  this.conversionService.convert(field, sourceType,elementDesc);
            target.add(targetElement);
        }
        return target;
    }
}
