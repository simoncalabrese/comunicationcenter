package comunicationcenter.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import comunicationcenter.common.builders.QueryParamsBuilder;
import comunicationcenter.common.components.AbstractPropertiesConfig;
import comunicationcenter.service.exceptions.BaseException;
import comunicationcenter.service.exceptions.Suppliers;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

/**
 * Created by simon.calabrese on 31/10/2017.
 */
public class AbstractService {

    @Autowired
    protected AbstractPropertiesConfig abstractPropertiesConfig;

    @Autowired
    private ObjectMapper mapper;

    private HttpURLConnection buildConnection(final QueryParamsBuilder builder, final ApiEnum apiEnum) throws IOException {
        final String urlAsString = abstractPropertiesConfig.getApi().get(apiEnum.getApiMapKey());
        final String queryParams = Optional.ofNullable(builder).map(QueryParamsBuilder::build).orElse("");
        final URL url = new URL(urlAsString + queryParams);
        return (HttpURLConnection) url.openConnection();
    }

    public <RETURN_CLASS> RETURN_CLASS doGetCall(final Class<RETURN_CLASS> clazz, final ApiEnum apiEnum, final QueryParamsBuilder builder) throws BaseException {
        try {
            final HttpURLConnection connection = buildConnection(builder, apiEnum);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() == 200) {
                return mapper.readValue(copyInputStream(connection.getInputStream()), clazz);
            } else {
                throw new BaseException(Suppliers.CONNECTION_RESULT_ERROR.get());

            }
        } catch (IOException e) {
            throw new BaseException(Suppliers.CONNECTION_ERROR.get());
        }
    }

    public <RETURN_CLASS, INPUT_CLASS> RETURN_CLASS doPostCall(final Class<RETURN_CLASS> clazz, final ApiEnum apiEnum, final INPUT_CLASS inputObject) throws BaseException {
        try {
            final String json = mapper.writeValueAsString(inputObject);
            final HttpURLConnection connection = buildConnection(null, apiEnum);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            final DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(json.getBytes("UTF-8"));
            dataOutputStream.flush();
            dataOutputStream.close();
            if (connection.getResponseCode() == 200) {
                return mapper.readValue(copyInputStream(connection.getInputStream()), clazz);
            } else {
                throw new BaseException(Suppliers.CONNECTION_RESULT_ERROR.get());
            }
        } catch (IOException e) {
            throw new BaseException(Suppliers.CONNECTION_ERROR.get());
        }
    }

    private InputStream copyInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] barr = new byte[256];
        int lenn;
        while ((lenn = inputStream.read(barr)) > -1) {
            baos.write(barr, 0, lenn);
        }
        baos.flush();
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
