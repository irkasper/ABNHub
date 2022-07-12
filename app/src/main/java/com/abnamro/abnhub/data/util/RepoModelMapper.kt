package com.abnamro.abnhub.data.util

import com.abnamro.abnhub.data.model.RepoDto
import com.abnamro.abnhub.data.model.RepoModel
import com.abnamro.abnhub.domain.util.Mapper

/**
 * Based on CLEAN Architecture
 * This class is using for mapping DTO objects (received from API) into Database Model
 */
class RepoModelMapper : Mapper<RepoDto, RepoModel> {

    override fun map(t: RepoDto): RepoModel {
        return RepoModel(
            name = t.name,
            fullName = t.fullName,
            description = t.description,
            avatarUrl = t.owner.avatarUrl,
            visibility = t.visibility,
            isPrivate = t.isPrivate,
        )
    }
}
