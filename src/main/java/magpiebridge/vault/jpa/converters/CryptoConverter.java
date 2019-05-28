package magpiebridge.vault.jpa.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import magpiebridge.vault.service.CryptoUtils;
import magpiebridge.vault.service.UserDetailsServiceImpl;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {
  
	@Override
	public String convertToDatabaseColumn(String entityData) {
		return CryptoUtils.encrypt(entityData, UserDetailsServiceImpl.getCurrentUserKey());
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return CryptoUtils.decrypt(dbData, UserDetailsServiceImpl.getCurrentUserKey());
	}
}
