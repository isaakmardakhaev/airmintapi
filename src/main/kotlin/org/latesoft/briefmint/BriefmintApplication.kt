package org.latesoft.briefmint

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BriefmintApplication

fun main(args: Array<String>) {
    runApplication<BriefmintApplication>(*args)
}
