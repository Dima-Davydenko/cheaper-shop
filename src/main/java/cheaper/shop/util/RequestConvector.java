package cheaper.shop.util;

public class RequestConvector {
    private static final String SPLIT_BY = "[, .%]+";
    private static final String LIKE = "%";

    public String prepareRequest(String searchRequest) {
        StringBuilder builder = new StringBuilder();
        String[] requestParts = searchRequest.split(SPLIT_BY);
        for (String requestPart : requestParts) {
            builder.append(LIKE).append(requestPart);
        }
        return builder.append(LIKE).toString();
    }
}
