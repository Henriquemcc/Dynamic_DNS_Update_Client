package dynamic.dns.update.client.console.common.myio

fun readString(instructionMessage: String? = null): String
{
    if (instructionMessage != null)
    {
        print(instructionMessage)
    }

    return readLine()?.trim() ?: ""
}