package com.greedy0110.searchbookkata.domain

class WrongOperationException(
    wrongKeyword: String,
    message: String? = "wrong keyword you wrote: $wrongKeyword\nyou can use or(|) and not(-) operator only once in one search."
) : Exception(message)