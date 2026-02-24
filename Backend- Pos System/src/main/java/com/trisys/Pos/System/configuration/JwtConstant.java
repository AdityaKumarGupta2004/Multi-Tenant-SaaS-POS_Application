package com.trisys.Pos.System.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class JwtConstant {
    public static final String JWT_SECRET = Dotenv.load().get("JWT_SECRET");
    public static final String JWT_HEADER = "Authorization";
}