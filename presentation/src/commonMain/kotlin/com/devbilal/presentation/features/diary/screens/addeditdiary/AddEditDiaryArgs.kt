package com.devbilal.presentation.features.diary.screens.addeditdiary

import com.devbilal.presentation.common.navigation.Route

interface AddEditDiaryArgs {
    val entryId: String?
}

class AddEditDiaryArgsImpl(
    route: Route.AddEditDiary
) : AddEditDiaryArgs {

    override val entryId = route.entryId
}
