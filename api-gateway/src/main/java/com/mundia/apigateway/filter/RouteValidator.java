package com.mundia.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> opeanApiEndPoints = List.of(
            "/api/auth/register",
            "/api/auth/authenticate"
    );
    public Predicate<ServerHttpRequest> isSecured =
            request -> opeanApiEndPoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
