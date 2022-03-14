package chap20

import kotlin.jvm.JvmStatic

class KotlinStudent {
    var name: String? = null
    private var id = 0

    constructor() {}
    constructor(name: String?, id: Int) {
        this.name = name
        this.id = id
    }

    constructor(name: String?) {
        this.name = name
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val s = KotlinStudent("Raoul", 1)
            println(s.name)
            s.id = 1337
            println(s.id)
        }
    }
}