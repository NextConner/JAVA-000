package com.jokergw.router.routers;

import java.util.List;

public interface HttpEndpointRouter {
    
    String route(List<String> endpoints);
    
}
