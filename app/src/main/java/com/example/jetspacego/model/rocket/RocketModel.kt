package com.example.jetspacego.model.rocket

data class RocketModel(
    val id: Int,
    val name: String,
    val description: String,
    val url: String
)

val rocketList = listOf(
    RocketModel(
        id = 1,
        name = "PSLV (Polar Satellite Launch Vehicle)",
        description = "An Indian launch vehicle developed by ISRO, used for launching satellites into polar orbits.",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/rocket_anim.glb"
    ),
    RocketModel(
        id = 2,
        name = "GSLV (Geosynchronous Satellite Launch Vehicle)",
        description = "India’s heavy-lift launch vehicle designed to place large payloads into geostationary orbits.",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/gslv_mk3.glb"
    ),
    RocketModel(
        id = 3,
        name = "Falcon 9",
        description = "A reusable two-stage rocket designed and manufactured by SpaceX for satellite and cargo missions.",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/falcon_9_-_spacex.glb"
    ),
    RocketModel(
        id = 4,
        name = "Mercury Atlas",
        description = "A launch vehicle used by NASA in the Mercury program to send astronauts into orbit.",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/atlas_lv-3b_mercury.glb"
    ),
    RocketModel(
        id = 5,
        name = "Mercury Redstone",
        description = "A smaller launch vehicle used to launch the first American astronauts into space in suborbital flights.",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/mercury-_redstone_launch_vehicle.glb"
    ),
    RocketModel(
        id = 6,
        name = "SLS (Space Launch System)",
        description = "NASA’s next-generation super heavy-lift rocket designed for deep space missions like Artemis.",
        url = "https://raw.githubusercontent.com/SmitKarwade/Rocket3d/main/space_launch_system_sls.glb"
    )
)