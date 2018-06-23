import React from 'react'
import { Row } from 'antd'
import List from '../components/REAPFA0003/List'
import styles from './REAPFA0003.less'

export default ({
  page, dispatch, effects, showCreateModal,
}) => {
  return (
    <div className={styles.container}>
      <Row>
        <List
          page={page}
          dispatch={dispatch}
          showCreateModal={showCreateModal}
          loading={effects['REAPFA0003/query']}
        />
      </Row>
    </div>
  )
}
