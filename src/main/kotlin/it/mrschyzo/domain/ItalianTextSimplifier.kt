package it.mrschyzo.domain

import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ItalianTextSimplifier : TextSimplifier {
    override fun simplify(text: String): String =
        text.lowercase(Locale.ITALY)
            .replace(Regex("[^a-zàèéìòù ]"), " ")
            .replace(Regex("([a-zàèéìòù])\\1{3,}")) {
                it.value.substring(0, 2)
            }.replace(Regex("( )\\1{2,}")) {
                it.value.substring(0, 1)
            }
}