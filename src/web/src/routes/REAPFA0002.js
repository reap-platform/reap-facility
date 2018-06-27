import React from 'react'
import { Row } from 'antd'
import RouteList from '../components/REAPFA0002/RouteList'
import styles from './REAPFA0002.less'

export default ({
  page, dispatch, effects, showCreateModal, applications,
}) => {
  return (
    <div className={styles.container}>
      <Row>
        <RouteList
          applications={applications}
          page={page}
          dispatch={dispatch}
          showCreateModal={showCreateModal}
          loading={effects['REAPFA0002/query']}
        />
      </Row>
    </div>
  )
}
