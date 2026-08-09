package com.devbilal.presentation.features.diary.screens.attachments

import com.devbilal.presentation.common.navigation.Route

interface AttachmentsArgs {
    val entryId: String
    val initialIndex: Int
}

class AttachmentsArgsImpl(
    route: Route.Attachments
) : AttachmentsArgs {
    override val entryId = route.entryId
    override val initialIndex = route.initialIndex
}
